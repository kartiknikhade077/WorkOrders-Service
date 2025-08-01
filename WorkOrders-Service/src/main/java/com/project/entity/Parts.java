package com.project.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Parts {
	@Id
	@UuidGenerator
	private String partId;
	private String partName;
	private long companyId;
	public Parts() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Parts(String partId, String partName, long companyId) {
		super();
		this.partId = partId;
		this.partName = partName;
		this.companyId = companyId;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	
	

}
