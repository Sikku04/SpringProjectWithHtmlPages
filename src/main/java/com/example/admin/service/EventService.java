package com.example.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admin.entity.Event;
import com.example.admin.repository.EventRepo;

@Service
public class EventService {

	@Autowired
	private EventRepo eventRepo;

	public void update(Event event) {

		if (event != null) {

			eventRepo.save(event);

		}

	}

}
