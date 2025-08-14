package com.project.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Processes {
	@Id
	@UuidGenerator
	private String processId;
	private String processName;
	private String companyId;
	
	public Processes() {
	}
	
	
	public Processes(String processId, String processName, String companyId) {
		super();
		this.processId = processId;
		this.processName = processName;
		this.companyId = companyId;
	}


	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
}
