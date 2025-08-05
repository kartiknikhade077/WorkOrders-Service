package com.project.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Material {
	
	@Id
	@UuidGenerator
	private String materialId;
	private String materialName;
	private String companyId;
	
	
	
	public Material() {
	}



	public Material(String materialId, String materialName, String companyId) {
		super();
		this.materialId = materialId;
		this.materialName = materialName;
		this.companyId = companyId;
	}



	public String getMaterialId() {
		return materialId;
	}



	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}



	public String getMaterialName() {
		return materialName;
	}



	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}



	public String getCompanyId() {
		return companyId;
	}



	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}



	@Override
	public String toString() {
		return "Material [materialId=" + materialId + ", materialName=" + materialName + ", companyId=" + companyId
				+ "]";
	}
	
	

}
