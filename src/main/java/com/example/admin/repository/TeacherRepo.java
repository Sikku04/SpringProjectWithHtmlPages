package com.example.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.entity.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {

}
