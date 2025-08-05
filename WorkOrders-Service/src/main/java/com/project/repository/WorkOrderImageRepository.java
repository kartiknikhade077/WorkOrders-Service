package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.WorkOrderImage;

public interface WorkOrderImageRepository extends JpaRepository<WorkOrderImage, String>  {
	
	List<WorkOrderImage> findByWorkOrderId(String workOrderId);

	void deleteByWorkOrderId(String workOrderId);

}
