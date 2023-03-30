package com.example.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admin.entity.Exam;
import com.example.admin.repository.ExamRepo;

@Service
public class ExamService {

	@Autowired
	private ExamRepo examRepo;

	public void update(Exam exam) {

		if (exam != null) {

			examRepo.save(exam);

		}

	}

}
