package com.example.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.entity.Exam;

public interface ExamRepo extends JpaRepository<Exam, Long> {

}
