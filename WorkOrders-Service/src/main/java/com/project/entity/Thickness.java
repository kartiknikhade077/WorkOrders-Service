package com.project.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Thickness {
	@Id
	@UuidGenerator
	private String thicknessId;
	private String thicknessName;
	private String companyId;
	
	
	
	public Thickness() {
	}

	public Thickness(String thicknessId, String thicknessName, String companyId) {
		super();
		this.thicknessId = thicknessId;
		this.thicknessName = thicknessName;
		this.companyId = companyId;
	}
	
	public String getThicknessId() {
		return thicknessId;
	}
	public void setThicknessId(String thicknessId) {
		this.thicknessId = thicknessId;
	}
	public String getThicknessName() {
		return thicknessName;
	}
	public void setThicknessName(String thicknessName) {
		this.thicknessName = thicknessName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "Thickness [thicknessId=" + thicknessId + ", thicknessName=" + thicknessName + ", companyId=" + companyId
				+ "]";
	}
	
}
