package com.example.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.entity.Event;

public interface EventRepo extends JpaRepository<Event, Long> {

}
