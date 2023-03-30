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

import com.example.admin.entity.Exam;
import com.example.admin.entity.Holiday;
import com.example.admin.repository.HolidayRepo;
import com.example.admin.service.HolidayService;
import com.example.admin.util.Constant;

@Controller
@RequestMapping("/demo")
public class HolidayController {
	private static final String uploadingDir = Constant.IMG_LOCATION;

	@Autowired
	private HolidayRepo holidayRepo;
	
	@Autowired
	private HolidayService holidayService;

	@GetMapping("/GetListOfHoliday")
	public String GetDeparetment(Model model) {

		List<Holiday> response = holidayRepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/holidayList";

	}

	@GetMapping("/addHolidayNotice")
	public String addHolidayNotice() {

		return "admin/notice/holidayNotice";

	}

	@PostMapping("/addHoliday")
	public String addHoliday(Holiday holiday) {
		System.out.println(":::::::department::::::" + holiday);

		holidayRepo.save(holiday);

		System.out.println(":::::department:::::::" + holiday.getId());

		return "redirect:/demo/GetHoliday";

	}

	@GetMapping("/GetHoliday")
	public String GetHoliday(Model model) {

		List<Holiday> response = holidayRepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/notices/holiday";

	}

	@GetMapping("/deleteHoliday/{id}")
	public String getDeleteHoliday(@PathVariable("id") Long id) {

		holidayRepo.deleteById(id);

		return "redirect:/demo/GetHoliday";

	}

	@GetMapping("/editHoliday/{id}")
	public String getEditHoliday(@PathVariable("id") Long id, Model model) {

		Optional<Holiday> userData = holidayRepo.findById(id);
		Holiday usData = userData.get();
		System.err.println("::::::::::::::userData::::" + userData);
		model.addAttribute("userData", usData);

		return "/admin/pages/notices/editHoliday";

	}

	@GetMapping("/holidayPage/{id}")
	public String getNoticePage(@PathVariable("id") Long id, Model model) {

		Optional<Holiday> userData = holidayRepo.findById(id);
		Holiday usData = userData.get();
		System.err.println("::::::::::::::userData::::" + userData);
		model.addAttribute("userData", usData);

		return "/admin/pages/notices/noticePage";

	}

	@PostMapping("/addHolidayById/{id}")
	public String addHolidayById(@PathVariable("id") Long id, Holiday holiday) {

		holidayRepo.save(holiday);

		return "redirect:/demo/GetHoliday";

	}

	@PostMapping("/addHolidayWithSignature")
	public String fileUpload(Holiday holiday, @RequestParam("file") MultipartFile file, Model model)
			throws IOException {

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

			holiday.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileNameWithRN);
			file.transferTo(fileDirectory);

			holiday.setPicUrl(uploadingDir);

			holidayRepo.save(holiday);

		} else {
			model.addAttribute("success", "Invalie User .!");
			return "redirect:/demo/GetHoliday";
		}
		model.addAttribute("success", "User Registration Successfully...!");
		return "redirect:/demo/GetHoliday";
	}

	@PostMapping("/updateHoliday/{id}")
	public String updateUser(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id, Model model,
			Holiday holiday, HttpServletRequest request) throws IOException {

		System.err.println(":::::.ExamController.addStudent::::" + holiday);

		Optional<Holiday> userData = holidayRepo.findById(holiday.getId());

		Holiday userInDB = userData.get();

		System.err.println(":::::.userInDB.userInDB::::" + userInDB.getPhotos());

		if (!file.getOriginalFilename().isEmpty()) {

			System.out.println(":::::.trueeeeeeeeeeeeeeeeeeeeeee::::" + holiday);

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
			holiday.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);
			holiday.setPicUrl(uploadingDir);

			holidayService.update(holiday);

			return "redirect:/demo/GetHoliday";

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
			holiday.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);
			holiday.setPicUrl(uploadingDir);

			holidayService.update(holiday);

			return "redirect:/demo/GetHoliday";
		}

	}

}
