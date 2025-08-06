package com.project.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.entity.WorkOrder;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, String> {
	
	WorkOrder findByWorkOrderId(String workOrderId);
	
	List<WorkOrder> findByProjectId(String projectId);
	
	Page<WorkOrder> findByCompanyIdAndCustomerNameContainingIgnoreCase(String companyId,String customerName,Pageable pageable );
	
	@Query("SELECT MAX(w.itemNo) FROM WorkOrder w WHERE w.companyId = :companyId")
	Integer findMaxItemNoByCompanyId(@Param("companyId") String companyId);

}
