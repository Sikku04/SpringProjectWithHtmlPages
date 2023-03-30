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

import com.example.admin.entity.Notification;
import com.example.admin.entity.Teacher;
import com.example.admin.repository.DepartmentRepo;
import com.example.admin.repository.NotificationRepo;
import com.example.admin.repository.StudentRepository;
import com.example.admin.repository.TeacherRepo;
import com.example.admin.repository.UserRepository;
import com.example.admin.service.UserService;
import com.example.admin.util.Constant;
import com.example.admin.util.EnumNotification;

@Controller
@RequestMapping("/demo")
public class TeacherController {

	private static final String uploadingDir = Constant.IMG_LOCATION;

	@Autowired
	private UserService userService;

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private TeacherRepo TeacherRepo;

	

	// ::::::::::::::::::::::::::::::Start Teacher ::::::::::::::::::::;;
	// ::::::::::::::::::::::::::::::Start Teacher ::::::::::::::::::::;;

	@GetMapping("/GetTeacher")
	public String GetTeacher(Model model) {

		List<Teacher> response = TeacherRepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/teacher";

	}

	@GetMapping("/addNewTeacher")
	public String addNewTeacher() {

		return "admin/addTeacher";

	}

	// Handling file upload request
	@PostMapping("/addTeacher")
	public String addTeacher(Teacher teacher, @RequestParam("file") MultipartFile file, Model model)
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

			teacher.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileNameWithRN);
			file.transferTo(fileDirectory);

			teacher.setPicUrl(uploadingDir);

			TeacherRepo.save(teacher);

			System.out.println(":::::student:::::::" + teacher.getId());

			Notification obj = new Notification(null, teacher.getId(), null,
					"A teacher has been signup " + teacher.getName(), Constant.getDateAndTime(),
					Constant.getDateAndTime(), EnumNotification.Teacher_SIGNUP);

			notificationRepo.save(obj);

		} else {
			model.addAttribute("success", "Invalie Student .!");
			return "redirect:/demo/GetTeacher";
		}
		model.addAttribute("success", "Add Student  Successfully...!");

		return "redirect:/demo/GetTeacher";
	}

	@GetMapping("/editTeacher/{id}")
	public String gewtEditTeacher(@PathVariable("id") Long id, Model model) {

		Optional<Teacher> userData = TeacherRepo.findById(id);
		Teacher usData = userData.get();
		System.err.println("::::::::::::::userData" + userData);
		model.addAttribute("userData", usData);

		return "admin/editTeacher";

	}

	@GetMapping("/deleteTeacher/{id}")
	public String getDeleteTeacher(@PathVariable("id") Long id) {

		TeacherRepo.deleteById(id);

		return "redirect:/demo/GetTeacher";

	}

	@PostMapping("/editTeacherById/{id}")
	public String editTeacher(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id, Model model,
			Teacher teacher, HttpServletRequest request) throws IOException {

		System.err.println(":::::.StudentController.addStudent::::" + teacher);

		Optional<Teacher> userData = TeacherRepo.findById(teacher.getId());

		Teacher userInDB = userData.get();

		System.err.println(":::::.userInDB.userInDB::::" + userInDB.getPhotos());

		if (!file.getOriginalFilename().isEmpty()) {

			System.out.println(":::::.Edit trueeeeeeeeeeeeeeeeeeeeeee::::" + teacher);

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
			teacher.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);

			userService.updateTeacher(teacher);

			return "redirect:/demo/GetTeacher";

		} else {

			System.out.println("::::Edit :.falseeeeeeeeeeeeeeeee::::" + userInDB.getPhotos());

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
			teacher.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);

			userService.updateTeacher(teacher);

			return "redirect:/demo/GetTeacher";
		}

	}

	// ::::::::::::::::::::::::::::::End Teacher ::::::::::::::::::::;;
	// ::::::::::::::::::::::::::::::End Teacher ::::::::::::::::::::;;

}
