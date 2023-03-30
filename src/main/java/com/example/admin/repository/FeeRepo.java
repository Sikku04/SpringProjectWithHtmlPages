package com.example.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.entity.Fee;

public interface FeeRepo extends JpaRepository<Fee, Long>{

}
