package com.example.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.entity.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

}
