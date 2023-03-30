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

import com.example.admin.entity.AllStudent;
import com.example.admin.entity.Notification;
import com.example.admin.repository.NotificationRepo;
import com.example.admin.repository.StudentRepository;
import com.example.admin.service.UserService;
import com.example.admin.util.Constant;
import com.example.admin.util.EnumNotification;

@Controller
@RequestMapping("/demo")
public class StudentController {

	private static final String uploadingDir = Constant.IMG_LOCATION;

	@Autowired
	private StudentRepository StudentRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private NotificationRepo notificationRepo;

	// ::::::::::::::::::::::::::::::Start Student ::::::::::::::::::::;;
	// ::::::::::::::::::::::::::::::Start Student ::::::::::::::::::::;;

	@GetMapping("/GetStudent")
	public String Getwidgets(Model model) {

		List<AllStudent> response = StudentRepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/student";

	}

	@GetMapping("/addNewStudent")
	public String addNewUser() {

		return "admin/addUser";

	}

	@GetMapping("/getTable")
	public String TableDemo() {

		return "admin/studentTable";

	}

	// Handling file upload request
	@PostMapping("/addStudent")
	public String addUser(AllStudent student, @RequestParam("file") MultipartFile file, Model model)
			throws IOException {

		// Save file on system
		if (!file.getOriginalFilename().isEmpty() && student != null) {

			int randNumbre = 0;

			String fileNameWithRN = null;

			// generate RandomNumber
			Random random = new Random();
			randNumbre = random.nextInt(1000);

			List<String> fileNames = new ArrayList<>();

			fileNameWithRN = randNumbre + file.getOriginalFilename();

			fileNames.add(fileNameWithRN);

			String imagepathurl = fileNameWithRN;

			student.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileNameWithRN);
			file.transferTo(fileDirectory);

			student.setPicUrl(uploadingDir);

			StudentRepo.save(student);

			System.out.println(":::::student:::::::" + student.getId());

			Notification obj = new Notification(student.getId(), null, null,
					"A Student has been signup " + student.getName(), Constant.getDateAndTime(),
					Constant.getDateAndTime(), EnumNotification.Student_SIGNUP);

			notificationRepo.save(obj);

		} else {
			model.addAttribute("success", "Invalie Student .!");
			return "redirect:/demo/addNewStudent";
		}
		model.addAttribute("success", "Add Student  Successfully...!");

		return "redirect:/demo/GetStudent";
	}

	@GetMapping("/editStudent/{id}")
	public String getEditUser(@PathVariable("id") Long id, Model model) {

		Optional<AllStudent> userData = StudentRepo.findById(id);
		AllStudent usData = userData.get();
		System.err.println("::::::::::::::userData" + userData);
		model.addAttribute("userData", usData);

		return "admin/editStudent";

	}

	@GetMapping("/deleteStudent/{id}")
	public String getDeleteUser(@PathVariable("id") Long id) {

		StudentRepo.deleteById(id);

		return "redirect:/demo/GetStudent";

	}

	@PostMapping("/editStudentById/{id}")
	public String editUser(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id, Model model,
			AllStudent student, HttpServletRequest request) throws IOException {

		System.err.println(":::::.StudentController.addStudent::::" + student);

		System.err.println(":::::.StudentController.addStudent::::" + student);

		Optional<AllStudent> userData = StudentRepo.findById(student.getId());

		AllStudent userInDB = userData.get();

		System.err.println(":::::.userInDB.userInDB::::" + userInDB.getPhotos());

		if (!file.getOriginalFilename().isEmpty()) {

			System.out.println(":::::.Edit trueeeeeeeeeeeeeeeeeeeeeee::::" + student);

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
			student.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);

			userService.updateStudent(student);

			return "redirect:/demo/GetStudent";

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
			student.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);

			userService.updateStudent(student);

			return "redirect:/demo/GetStudent";
		}

	}

	// ::::::::::::::::::::Student End:::::::::::::::::

}
