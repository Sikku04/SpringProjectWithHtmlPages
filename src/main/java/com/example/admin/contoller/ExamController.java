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
import com.example.admin.entity.User;
import com.example.admin.repository.ExamRepo;
import com.example.admin.service.ExamService;
import com.example.admin.util.Constant;

@Controller
@RequestMapping("/demo")
public class ExamController {

	private static final String uploadingDir = Constant.IMG_LOCATION;

	@Autowired
	private ExamRepo examRepo;
	@Autowired
	private ExamService examService;

	@GetMapping("/GetListOfExam")
	public String GetDeparetment(Model model) {

		List<Exam> response = examRepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/examList";

	}

	@GetMapping("/addExamNotice")
	public String addExamNotice() {

		return "admin/notice/examNotice";

	}

	@PostMapping("/addExamWithSignature")
	public String fileUpload(Exam exam, @RequestParam("file") MultipartFile file, Model model) throws IOException {

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

			exam.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileNameWithRN);
			file.transferTo(fileDirectory);

			exam.setPicUrl(uploadingDir);

			examRepo.save(exam);

		} else {
			model.addAttribute("success", "Invalie User .!");
			return "redirect:/demo/GetExam";
		}
		model.addAttribute("success", "User Registration Successfully...!");
		return "redirect:/demo/GetExam";
	}

	@PostMapping("/addExam")
	public String addExam(Exam exam) {
		System.out.println(":::::::exam::::::" + exam);

		examRepo.save(exam);

		System.out.println(":::::department:::::::" + exam.getId());

		return "redirect:/demo/GetExam";

	}

	@GetMapping("/GetExam")
	public String GetExam(Model model) {

		List<Exam> response = examRepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/notices/exam";

	}

	@GetMapping("/deleteExam/{id}")
	public String getDeleteExam(@PathVariable("id") Long id) {

		examRepo.deleteById(id);

		return "redirect:/demo/GetExam";

	}

	@GetMapping("/editExam/{id}")
	public String getEditExam(@PathVariable("id") Long id, Model model) {

		Optional<Exam> userData = examRepo.findById(id);
		Exam usData = userData.get();
		System.err.println("::::::::::::::userData::::" + userData);
		model.addAttribute("userData", usData);

		return "/admin/pages/notices/editExam";

	}

	@PostMapping("/addExamById/{id}")
	public String addExamById(@PathVariable("id") Long id, Exam exam) {

		examRepo.save(exam);

		return "redirect:/demo/GetExam";

	}
	
	
	
	

	@GetMapping("/examPage/{id}")
	public String getNoticePage(@PathVariable("id") Long id, Model model) {

		Optional<Exam> userData = examRepo.findById(id);
		Exam usData = userData.get();
		System.err.println("::::::::::::::userData::::" + userData);
		model.addAttribute("userData", usData);

		return "/admin/pages/notices/noticePage";

	}

	
	
	@PostMapping("/updateExam/{id}")
	public String updateUser(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id, Model model,
			Exam exam, HttpServletRequest request) throws IOException {

		System.err.println(":::::.ExamController.addStudent::::" + exam);

		Optional<Exam> userData = examRepo.findById(exam.getId());

		Exam userInDB = userData.get();

		System.err.println(":::::.userInDB.userInDB::::" + userInDB.getPhotos());

		if (!file.getOriginalFilename().isEmpty()) {

			System.out.println(":::::.trueeeeeeeeeeeeeeeeeeeeeee::::" + exam);

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
			exam.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);
			exam.setPicUrl(uploadingDir);

			examService.update(exam);

			return "redirect:/demo/GetExam";

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
			exam.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);
			exam.setPicUrl(uploadingDir);

			examService.update(exam);

			return "redirect:/demo/GetExam";
		}

	}

}
