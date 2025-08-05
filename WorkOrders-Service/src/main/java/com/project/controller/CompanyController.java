package com.project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import com.project.client.UserSerivceClinet;
import com.project.dto.Company;
import com.project.entity.Material;
import com.project.entity.Parts;
import com.project.entity.Thickness;
import com.project.entity.WorkOrder;
import com.project.entity.WorkOrderImage;
import com.project.entity.WorkOrderItems;
import com.project.repository.MaterialRepository;
import com.project.repository.PartsRepository;
import com.project.repository.ThicknessRepository;
import com.project.repository.WorkOrderImageRepository;
import com.project.repository.WorkOrderItemsRepository;
import com.project.repository.WorkOrderRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/work")
public class CompanyController {

	@Autowired
	private UserSerivceClinet userSerivceClinet;
	@Autowired
	private WorkOrderRepository workOrderRepository;

	@Autowired
	private WorkOrderItemsRepository workOrderItemsRepository;

	@Autowired
	private WorkOrderImageRepository workOrderImageRepository;
	
	@Autowired
	private ThicknessRepository thicknessRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private PartsRepository partsRepository;

	Company company;

	@ModelAttribute
	public void companyDetails() {

		company = userSerivceClinet.getCompanyInfo();

	}

	@PostMapping(value = "/createWorkOrder", consumes = { "multipart/form-data" })
	public ResponseEntity<String> createWorkOrder(
			@RequestPart(value = "workOrder", required = false) String workOrderJson,
			@RequestPart(value = "workOrderItems", required = false) String workOrderItemsJson,
			@RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException

	{
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			// Convert JSON strings to entities
			WorkOrder workOrder = objectMapper.readValue(workOrderJson, WorkOrder.class);

			List<WorkOrderItems> workOrderItems = objectMapper.readValue(workOrderItemsJson,
					new TypeReference<List<WorkOrderItems>>() {
					});

			// Save work order
			workOrder.setCompanyId(company.getCompanyId());
			workOrderRepository.save(workOrder);

			// Save work order items
			for (WorkOrderItems item : workOrderItems) {
				item.setItemNo(workOrder.getItemNo());
				item.setCompanyId(company.getCompanyId());
				item.setWorkOrderId(workOrder.getWorkOrderId());
				item.setEmployeeId(null);
				workOrderItemsRepository.save(item);
			}

			// Save images to local folder
			if (images != null && images.length > 0) {
				for (MultipartFile file : images) {
					WorkOrderImage workOrderImage = new WorkOrderImage();
					workOrderImage.setImage(file.getBytes());
					workOrderImage.setWorkOrderId(workOrder.getWorkOrderId());
					workOrderImageRepository.save(workOrderImage);
	
				}
			}

			return ResponseEntity.ok("Work order, items, and images saved successfully.");
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error creating employee: " + e.getMessage());
		}
	}

	@PutMapping(value = "/updateWorkOrder", consumes = { "multipart/form-data" })
	public ResponseEntity<String> updateWorkOrder(
			@RequestPart(value = "workOrder", required = false) String workOrderJson,
			@RequestPart(value = "workOrderItems", required = false) String workOrderItemsJson) throws IOException

	{
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			// Convert JSON strings to entities
			WorkOrder workOrder = objectMapper.readValue(workOrderJson, WorkOrder.class);

			List<WorkOrderItems> workOrderItems = objectMapper.readValue(workOrderItemsJson,
					new TypeReference<List<WorkOrderItems>>() {
					});

			// Save work order
			workOrder.setCompanyId(company.getCompanyId());
			workOrderRepository.save(workOrder);

			// Save work order items
			for (WorkOrderItems item : workOrderItems) {
				item.setItemNo(workOrder.getItemNo());
				item.setCompanyId(company.getCompanyId());
				item.setWorkOrderId(workOrder.getWorkOrderId());
				item.setEmployeeId(null);
				workOrderItemsRepository.save(item);
			}

			return ResponseEntity.ok("Work order, items, and images saved successfully.");
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error creating employee: " + e.getMessage());
		}
	}

	@GetMapping("/getWorkOrderById/{workOrderId}")
	public ResponseEntity<?> getWorkOrderById(@PathVariable String workOrderId) {

		try {
			Map<String, Object> data = new HashMap<>();

			WorkOrder workOrder = workOrderRepository.findByWorkOrderId(workOrderId);

			List<WorkOrderItems> workOrderItems = workOrderItemsRepository.findByWorkOrderId(workOrderId);

			List<WorkOrderImage> workImages = workOrderImageRepository.findByWorkOrderId(workOrderId);

			data.put("workOrder", workOrder);
			data.put("workOrderItems", workOrderItems);
			data.put("workImages", workImages);

			return ResponseEntity.ok(data);
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error creating employee: " + e.getMessage());
		}
	}

	@GetMapping("/getAllWorkOrders/{page}/{size}")
	public ResponseEntity<?> getAllWorkOrders(@PathVariable int page, @PathVariable int size,
			@RequestParam(defaultValue = "") String customerName) {
		try {

			Map<String, Object> data = new HashMap<String, Object>();
			Pageable pageable = PageRequest.of(page, size, Sort.by("workOrderId").descending());

			Page<WorkOrder> workOrder = workOrderRepository
					.findByCompanyIdAndCustomerNameContainingIgnoreCase(company.getCompanyId(), customerName, pageable);
			List<WorkOrder> workOrderList = workOrder.getContent();
			data.put("workOrderList", workOrderList);
			data.put("totalPages", workOrder.getTotalPages());
			data.put("currentPage", workOrder.getNumber());
			return ResponseEntity.ok(data);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}

	@GetMapping("/getMaxItemNumber")
	public ResponseEntity<?> getMaxItemNumber() {

		try {
			int maxItemNum = workOrderRepository.findMaxItemNoByCompanyId(company.getCompanyId());
			return ResponseEntity.ok(maxItemNum);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteWorkOrderItem/{itemId}")
	public ResponseEntity<?> deleteWorkOrderItem(@PathVariable("itemId") String itemId){
		if (!workOrderItemsRepository.existsById(itemId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Work order item with ID " + itemId + " not found.");
        }

        workOrderItemsRepository.deleteById(itemId);
        return ResponseEntity.ok("Work order item deleted successfully.");
	}
	
	@DeleteMapping("/deleteWorkOrderImage/{workOrderImageId}")
    public ResponseEntity<String> deleteWorkOrderImage(@PathVariable("workOrderImageId") String workOrderImageId) {
        Optional<WorkOrderImage> imageOpt = workOrderImageRepository.findById(workOrderImageId);
        if (imageOpt.isPresent()) {
            workOrderImageRepository.deleteById(workOrderImageId);
            return ResponseEntity.ok("Image deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found.");
        }
    }
	
	@PostMapping(value="/newWorkOrderImages",consumes = { "multipart/form-data" })
	public ResponseEntity<?> addNewImages(
			@RequestPart(value = "workOrderId", required = false) String workOrderId,
			@RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException{
		
		if (images != null && images.length > 0) {
			for (MultipartFile file : images) {
				WorkOrderImage workOrderImage = new WorkOrderImage();
				workOrderImage.setImage(file.getBytes());
				workOrderImage.setWorkOrderId(workOrderId);
				workOrderImageRepository.save(workOrderImage);
			}
			return ResponseEntity.ok("Images saved");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not provided.");
		
	}
	
	@GetMapping("/getWorkOrderItemsByProjectId/{projectId}")
	public ResponseEntity<?> getWorkOrderItemsByProjectId(@PathVariable String projectId) {

		try {
			List<WorkOrderItems> list = workOrderItemsRepository.findByProjectId(projectId);
			return ResponseEntity.ok(list);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
		
	}
	
	@PostMapping("/addThickness/{addThickness}")
	public ResponseEntity<?> addThickness(@PathVariable("addThickness") String name){
		try {
			Thickness thickness = new Thickness();
			thickness.setThicknessName(name);
			thickness.setCompanyId(company.getCompanyId());
			Thickness savedThickness = thicknessRepository.save(thickness);
			return ResponseEntity.ok(savedThickness);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
		
	}
	
	@GetMapping("/getAllThicknesses")
	public ResponseEntity<?> getAllThicknesses(){
		try {
			List<Thickness> allThicknesses = thicknessRepository.findAllByCompanyId(company.getCompanyId());
			return ResponseEntity.ok(allThicknesses);
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteThickness/{thicknessId}")
	public ResponseEntity<?> deleteThickness(@PathVariable("thicknessId") String thicknessId) {
	    try {
	        Optional<Thickness> optionalThickness = thicknessRepository.findById(thicknessId);

	        if (optionalThickness.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .body("Thickness with ID " + thicknessId + " not found");
	        }

	        thicknessRepository.deleteById(thicknessId);
	        return ResponseEntity.ok("Thickness deleted successfully");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error deleting thickness: " + e.getMessage());
	    }
	}
	
	@PutMapping("/updateThickness/{thicknessId}")
	public ResponseEntity<?> updateThickness(@PathVariable String thicknessId, @RequestBody Thickness updated) {
	    Optional<Thickness> optional = thicknessRepository.findById(thicknessId);
	    if (optional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");

	    Thickness thickness = optional.get();
	    thickness.setThicknessName(updated.getThicknessName());
	    thicknessRepository.save(thickness);
	    return ResponseEntity.ok(thickness);
	}
	
	@PostMapping("/addMaterial/{materialName}")
    public ResponseEntity<?> addMaterial(@PathVariable("materialName") String materialName) {
        try {
            Material material = new Material();
            material.setMaterialName(materialName);
            material.setCompanyId(company.getCompanyId());
            Material savedMaterial = materialRepository.save(material);
            return ResponseEntity.ok(savedMaterial);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error " + e.getMessage());
        }
    }

    @GetMapping("/getAllMaterials")
    public ResponseEntity<?> getAllMaterials() {
        try {
            List<Material> allMaterials = materialRepository.findAllByCompanyId(company.getCompanyId());
            return ResponseEntity.ok(allMaterials);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteMaterial/{materialId}")
    public ResponseEntity<?> deleteMaterial(@PathVariable("materialId") String materialId) {
        try {
            Optional<Material> optional = materialRepository.findById(materialId);
            if (optional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Material with ID " + materialId + " not found");
            }

            materialRepository.deleteById(materialId);
            return ResponseEntity.ok("Material deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting material: " + e.getMessage());
        }
    }

 
    @PutMapping("/updateMaterial/{materialId}")
    public ResponseEntity<?> updateMaterial(@PathVariable String materialId,
                                            @RequestBody Material updated) {
        Optional<Material> optional = materialRepository.findById(materialId);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material not found");
        }

        Material material = optional.get();
        material.setMaterialName(updated.getMaterialName());
        materialRepository.save(material);
        return ResponseEntity.ok(material);
    }
    
    @PostMapping("/addPart/{partName}")
    public ResponseEntity<?> addPart(@PathVariable("partName") String name) {
        try {
            Parts part = new Parts();
            part.setPartName(name);
            part.setCompanyId(company.getCompanyId());
            Parts saved = partsRepository.save(part);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
        }
    }

    @GetMapping("/getAllParts")
    public ResponseEntity<?> getAllParts() {
        try {
            List<Parts> allParts = partsRepository.findAllByCompanyId(company.getCompanyId());
            return ResponseEntity.ok(allParts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
        }
    }

    @DeleteMapping("/deletePart/{partId}")
    public ResponseEntity<?> deletePart(@PathVariable("partId") String partId) {
        try {
            Optional<Parts> optional = partsRepository.findById(partId);
            if (optional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Part not found");
            }
            partsRepository.deleteById(partId);
            return ResponseEntity.ok("Part deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting part: " + e.getMessage());
        }
    }

    @PutMapping("/updatePart/{partId}")
    public ResponseEntity<?> updatePart(@PathVariable String partId, @RequestBody Parts updated) {
        Optional<Parts> optional = partsRepository.findById(partId);
        if (optional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");

        Parts part = optional.get();
        part.setPartName(updated.getPartName());
        partsRepository.save(part);
        return ResponseEntity.ok(part);
    }
    
    @DeleteMapping("/deleteWorkOrder/{workOrderId}")
    @Transactional
    public ResponseEntity<String> deleteWorkOrder(@PathVariable("workOrderId") String workOrderId) {
        try {
        	
            List<WorkOrderImage> images = workOrderImageRepository.findByWorkOrderId(workOrderId);
            for (WorkOrderImage image : images) {
                workOrderImageRepository.delete(image);
            }
            
            List<WorkOrderItems> items = workOrderItemsRepository.findByWorkOrderId(workOrderId);
            for (WorkOrderItems item : items) {
                workOrderItemsRepository.delete(item);
            }
            
            if (workOrderRepository.existsById(workOrderId)) {
                workOrderRepository.deleteById(workOrderId);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Work Order not found.");
            }

            return ResponseEntity.ok("Work Order and related data deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete Work Order: " + e.getMessage());
        }
    }

}
