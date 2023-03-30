package com.example.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.entity.Department;
import com.example.admin.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String emial);

	Optional<User> findByOtp(String otp);

	User findByEmailAndPassword(String email, String password);

}
