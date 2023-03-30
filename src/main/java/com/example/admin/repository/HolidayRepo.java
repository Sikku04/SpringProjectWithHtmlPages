package com.example.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.entity.Holiday;

public interface HolidayRepo extends JpaRepository<Holiday, Long> {

}
