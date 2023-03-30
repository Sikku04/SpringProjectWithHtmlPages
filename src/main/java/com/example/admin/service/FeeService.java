package com.example.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admin.entity.Fee;
import com.example.admin.repository.FeeRepo;

@Service
public class FeeService {

	@Autowired
	private FeeRepo feeRepo;

	public void update(Fee fee) {

		if (fee != null) {

			feeRepo.save(fee);

		}
	}

}
