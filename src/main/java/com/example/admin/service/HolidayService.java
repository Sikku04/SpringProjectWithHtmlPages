package com.example.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admin.entity.Holiday;
import com.example.admin.repository.HolidayRepo;

@Service
public class HolidayService {

	@Autowired
	private HolidayRepo holidayRepo;

	public void update(Holiday holiday) {

		if (holiday != null) {
			holidayRepo.save(holiday);
		}

	}

}
