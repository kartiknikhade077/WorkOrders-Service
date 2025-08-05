package com.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, String> {

	List<Material> findAllByCompanyId(String companyId);
}
