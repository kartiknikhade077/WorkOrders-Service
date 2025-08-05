package com.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.entity.Parts;

public interface PartsRepository extends JpaRepository<Parts, String> {

	List<Parts> findAllByCompanyId(String companyId);
}
