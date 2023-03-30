package com.example.admin.contoller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.admin.entity.Department;
import com.example.admin.entity.Event;
import com.example.admin.entity.Holiday;
import com.example.admin.repository.EventRepo;
import com.example.admin.service.EventService;
import com.example.admin.util.Constant;

@Controller
@RequestMapping("/demo")
public class EventController {

	private static final String uploadingDir = Constant.IMG_LOCATION;

	@Autowired
	private EventRepo eventRepo;

	@Autowired
	private EventService eventService;

	@GetMapping("/GetListOfEvent")
	public String GetDeparetment(Model model) {

		List<Event> response = eventRepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/eventList";

	}

	@GetMapping("/addEventsNotice")
	public String addEventsNotice() {

		return "admin/notice/eventNotice";

	}

	@PostMapping("/addEvent")
	public String addEventsNotice(Event event) {
		System.out.println(":::::::exam::::::" + event);

		eventRepo.save(event);

		System.out.println(":::::department:::::::" + event.getId());

		return "redirect:/demo/GetEvents";

	}

	@GetMapping("/GetEvents")
	public String GetEvents(Model model) {

		List<Event> response = eventRepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/notices/event";

	}

	@GetMapping("/deleteEvent/{id}")
	public String getDeleteEvent(@PathVariable("id") Long id) {

		eventRepo.deleteById(id);

		return "redirect:/demo/GetEvents";

	}

	@GetMapping("/editEvent/{id}")
	public String getEditEvent(@PathVariable("id") Long id, Model model) {

		Optional<Event> userData = eventRepo.findById(id);
		Event usData = userData.get();
		System.err.println("::::::::::::::userData::::" + userData);
		model.addAttribute("userData", usData);

		return "/admin/pages/notices/editEvent";

	}

	@PostMapping("/addEventById/{id}")
	public String addEventById(@PathVariable("id") Long id, Event event) {

		eventRepo.save(event);

		return "redirect:/demo/GetEvents";

	}

	@GetMapping("/eventPage/{id}")
	public String getNoticePage(@PathVariable("id") Long id, Model model) {

		Optional<Event> userData = eventRepo.findById(id);
		Event usData = userData.get();
		System.err.println("::::::::::::::userData::::" + userData);
		model.addAttribute("userData", usData);

		return "/admin/pages/notices/noticePage";

	}

	@PostMapping("/addEventWithSignature")
	public String fileUpload(Event event, @RequestParam("file") MultipartFile file, Model model) throws IOException {

		// Save file on system
		if (!file.getOriginalFilename().isEmpty()) {

			int randNumbre = 0;

			String fileNameWithRN = null;

			// generate RandomNumber
			Random random = new Random();
			randNumbre = random.nextInt(1000);

			List<String> fileNames = new ArrayList<>();

			fileNameWithRN = randNumbre + file.getOriginalFilename();

			fileNames.add(fileNameWithRN);

			String imagepathurl = fileNameWithRN;

			event.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileNameWithRN);
			file.transferTo(fileDirectory);

			event.setPicUrl(uploadingDir);

			eventRepo.save(event);

		} else {
			model.addAttribute("success", "Invalie User .!");
			return "redirect:/demo/GetEvents";
		}
		model.addAttribute("success", "User Registration Successfully...!");
		return "redirect:/demo/GetEvents";
	}

	@PostMapping("/updateEvent/{id}")
	public String updateUser(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id, Model model,
			Event event, HttpServletRequest request) throws IOException {

		System.err.println(":::::.ExamController.addStudent::::" + event);

		Optional<Event> userData = eventRepo.findById(event.getId());

		Event userInDB = userData.get();

		System.err.println(":::::.userInDB.userInDB::::" + userInDB.getPhotos());

		if (!file.getOriginalFilename().isEmpty()) {

			System.out.println(":::::.trueeeeeeeeeeeeeeeeeeeeeee::::" + event);

			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(
					new File("src/main/resources/static/admin/dist/img/", file.getOriginalFilename())));
			outputStream.write(file.getBytes());
			outputStream.flush();
			outputStream.close();
			int rand_int2 = 0;
			String fileName = null;
			Random random = new Random();
			rand_int2 = random.nextInt(1000);
			List<String> fileNames = new ArrayList<>();

			fileName = rand_int2 + file.getOriginalFilename();
			fileNames.add(fileName);
			String imagepathurl = fileName;
			event.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);
			event.setPicUrl(uploadingDir);

			eventService.update(event);

			return "redirect:/demo/GetEvents";

		} else {

			System.out.println(":::::.falseeeeeeeeeeeeeeeee::::" + userInDB.getPhotos());

			BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/admin/dist/img/", userInDB.getPhotos())));

			outputStream.write(file.getBytes());
			outputStream.flush();
			outputStream.close();

			String fileName = null;

			List<String> fileNames = new ArrayList<>();

			fileName = userInDB.getPhotos();

			fileNames.add(fileName);
			String imagepathurl = fileName;
			event.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);
			event.setPicUrl(uploadingDir);

			eventService.update(event);

			return "redirect:/demo/GetEvents";
		}

	}

}
