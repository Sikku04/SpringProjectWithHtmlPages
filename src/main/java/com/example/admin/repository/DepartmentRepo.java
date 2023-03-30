package com.example.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

	

}
