package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.WorkOrderItems;

public interface WorkOrderItemsRepository extends JpaRepository<WorkOrderItems, String> {
	
	List<WorkOrderItems> findByWorkOrderId(String wrokOrderId);

}
