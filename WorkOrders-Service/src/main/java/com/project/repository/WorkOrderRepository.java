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
	
	@Query("""
			SELECT w FROM WorkOrder w 
			WHERE w.companyId = :companyId 
			  AND (
			       LOWER(w.customerName) LIKE LOWER(CONCAT('%', :search, '%')) 
			    OR CAST(w.itemNo AS string) LIKE CONCAT('%', :search, '%')
			    OR LOWER(w.projectName) LIKE LOWER(CONCAT('%', :search, '%'))
			    OR LOWER(w.material) LIKE LOWER(CONCAT('%', :search, '%'))
			    OR LOWER(w.partName) LIKE LOWER(CONCAT('%', :search, '%'))
			    OR LOWER(w.partSize) LIKE LOWER(CONCAT('%', :search, '%'))
			    OR LOWER(w.partWeight) LIKE LOWER(CONCAT('%', :search, '%'))
			    OR LOWER(w.partNumber) LIKE LOWER(CONCAT('%', :search, '%'))
			  )
			""")
			Page<WorkOrder> searchByCompanyAndAnyField(
			    @Param("companyId") String companyId,
			    @Param("search") String search,
			    Pageable pageable
			);

	
	
	@Query("SELECT COALESCE(MAX(w.itemNo), 0) FROM WorkOrder w WHERE w.companyId = :companyId")
	Integer findMaxItemNoByCompanyId(@Param("companyId") String companyId);

	boolean existsByItemNo(Integer itemNo);
	
	
	@Query("SELECT w.itemNo FROM WorkOrder w WHERE w.companyId = :companyId order by itemNo desc")
	List<Integer> findItemNosByCompanyId(@Param("companyId") String companyId);

	
	WorkOrder findByItemNo(Integer itemNo);

}
