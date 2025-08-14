package com.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Thickness;
import com.project.entity.WorkOrderProcesses;

public interface WorkOrderProcessesRepository extends JpaRepository<WorkOrderProcesses, String> {

	List<WorkOrderProcesses> findAllByCompanyId(String processId);
}
