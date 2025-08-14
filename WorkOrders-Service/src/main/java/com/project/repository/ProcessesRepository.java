package com.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Processes;
import com.project.entity.Thickness;
import com.project.entity.WorkOrderProcesses;

public interface ProcessesRepository extends JpaRepository<Processes, String> {

	List<Processes> findAllByCompanyId(String processId);
}
