package com.project.entity;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;

@Entity
public class WorkOrder {

    @Id
    @UuidGenerator
    private String workOrderId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "customer_name", length = 255)
    private String customerName;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "item_no")
    private Integer itemNo;

    @Column(name = "material", length = 255)
    private String material;

    @Column(name = "part_name", length = 255)
    private String partName;

    @Column(name = "part_size", length = 255)
    private String partSize;

    @Column(name = "part_weight", length = 255)
    private String partWeight;

    @Column(name = "project_name", length = 255)
    private String projectName;

    @Column(name = "thickness")
    private Float thickness;
    
    

    // Getters and Setters

    public WorkOrder(String workOrderId, Long companyId, String customerName, Long employeeId, Integer itemNo,
			String material, String partName, String partSize, String partWeight, String projectName, Float thickness) {
		super();
		this.workOrderId = workOrderId;
		this.companyId = companyId;
		this.customerName = customerName;
		this.employeeId = employeeId;
		this.itemNo = itemNo;
		this.material = material;
		this.partName = partName;
		this.partSize = partSize;
		this.partWeight = partWeight;
		this.projectName = projectName;
		this.thickness = thickness;
	}

    
	public WorkOrder() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartSize() {
        return partSize;
    }

    public void setPartSize(String partSize) {
        this.partSize = partSize;
    }

    public String getPartWeight() {
        return partWeight;
    }

    public void setPartWeight(String partWeight) {
        this.partWeight = partWeight;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Float getThickness() {
        return thickness;
    }

    public void setThickness(Float thickness) {
        this.thickness = thickness;
    }
}
