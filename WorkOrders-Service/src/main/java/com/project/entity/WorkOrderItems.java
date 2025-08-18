package com.project.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;

@Entity
public class WorkOrderItems {

	@Id
	@UuidGenerator
	private String itemId;

	@Column(name = "cancel")
	private Boolean cancel;

	@Column(name = "company_id")
	private String companyId;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "height")
	private Double height;

	@Column(name = "item_no")
	private Integer itemNo;

	@Column(name = "length")
	private Double length;

	@Column(name = "operation_number")
	private Integer operationNumber;

	@Column(name = "proceess", length = 255)
	private String proceess; // You might want to rename this to 'process' in DB and code for correctness.

	@Column(name = "remark", length = 255)
	private String remark;

	@Column(name = "scope")
	private Boolean scope;

	@Column(name = "width")
	private Double width;

	@Column(name = "work_order_id", length = 255)
	private String workOrderId;

	@Column(name = "work_order_no", length = 255)
	private String workOrderNo;
	
	@Column(name = "parent_work_order_no", length = 255)
	private String parentWorkOrderNo;

	// Getters and Setters

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Boolean getCancel() {
		return cancel;
	}

	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Integer getOperationNumber() {
		return operationNumber;
	}

	public void setOperationNumber(Integer operationNumber) {
		this.operationNumber = operationNumber;
	}

	public String getProceess() {
		return proceess;
	}

	public void setProceess(String proceess) {
		this.proceess = proceess;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getScope() {
		return scope;
	}

	public void setScope(Boolean scope) {
		this.scope = scope;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getParentWorkOrderNo() {
		return parentWorkOrderNo;
	}

	public void setParentWorkOrderNo(String parentWorkOrderNo) {
		this.parentWorkOrderNo = parentWorkOrderNo;
	}
	
}
