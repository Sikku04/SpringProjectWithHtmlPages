package com.example.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.entity.AllStudent;

public interface StudentRepository extends JpaRepository<AllStudent, Long> {

	List<AllStudent> findByStandard(String standard);

	

}
