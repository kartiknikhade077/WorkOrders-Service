package com.project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.client.UserSerivceClinet;
import com.project.dto.Company;
import com.project.entity.WorkOrder;
import com.project.entity.WorkOrderImage;
import com.project.entity.WorkOrderItems;
import com.project.repository.WorkOrderImageRepository;
import com.project.repository.WorkOrderItemsRepository;
import com.project.repository.WorkOrderRepository;

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
				item.setEmployeeId(Long.valueOf(0));
				workOrderItemsRepository.save(item);
			}

			// Save images to local folder
			for (MultipartFile file : images) {
				WorkOrderImage workOrderImage = new WorkOrderImage();
				workOrderImage.setImage(file.getBytes());
				workOrderImage.setWorkOrderId(workOrder.getWorkOrderId());
				workOrderImageRepository.save(workOrderImage);

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
				item.setEmployeeId(Long.valueOf(0));
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

}
