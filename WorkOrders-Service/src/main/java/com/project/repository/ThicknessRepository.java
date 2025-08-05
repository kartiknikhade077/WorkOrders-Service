package com.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Thickness;

public interface ThicknessRepository extends JpaRepository<Thickness, String> {

	List<Thickness> findAllByCompanyId(String companyId);
}
