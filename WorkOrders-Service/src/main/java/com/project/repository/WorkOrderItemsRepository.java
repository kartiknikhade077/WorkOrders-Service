package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.entity.WorkOrderItems;

public interface WorkOrderItemsRepository extends JpaRepository<WorkOrderItems, String> {
	
	List<WorkOrderItems> findByWorkOrderId(String wrokOrderId);
	
	@Query("SELECT w FROM WorkOrderItems w WHERE w.workOrderId IN " +
	           "(SELECT wo.workOrderId FROM WorkOrder wo WHERE wo.projectId = :projectId)")
	    List<WorkOrderItems> findByProjectId(@Param("projectId") String projectId);

	void deleteByWorkOrderId(String workOrderId);

}
