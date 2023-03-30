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

import com.example.admin.entity.Event;
import com.example.admin.entity.Exam;
import com.example.admin.entity.Fee;
import com.example.admin.repository.FeeRepo;
import com.example.admin.service.FeeService;
import com.example.admin.util.Constant;

@Controller
@RequestMapping("/demo")
public class FeeController {

	private static final String uploadingDir = Constant.IMG_LOCATION;

	@Autowired
	private FeeRepo feeRepo;

	@Autowired
	private FeeService feeService;

	@GetMapping("/listOfFee")
	public String GetDeparetment(Model model) {

		List<Fee> response = feeRepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/feeList";

	}

	@GetMapping("/addFeeNotice")
	public String addFeeNotice() {

		return "admin/notice/FeeNotice";

	}

	@PostMapping("/addFee")
	public String addFeeNotice(Fee fee) {
		System.out.println(":::::::exam::::::" + fee);

		feeRepo.save(fee);

		System.out.println(":::::department:::::::" + fee.getId());

		return "redirect:/demo/GetFee";

	}

	@GetMapping("/GetFee")
	public String GetFee(Model model) {

		List<Fee> response = feeRepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/notices/fee";

	}

	@GetMapping("/deleteFee/{id}")
	public String getDeleteFee(@PathVariable("id") Long id) {

		feeRepo.deleteById(id);

		return "redirect:/demo/GetFee";

	}

	@GetMapping("/editFee/{id}")
	public String getEditFee(@PathVariable("id") Long id, Model model) {

		Optional<Fee> userData = feeRepo.findById(id);
		Fee usData = userData.get();
		System.err.println("::::::::::::::userData::::" + userData);
		model.addAttribute("userData", usData);

		return "/admin/pages/notices/editFee";

	}

	@PostMapping("/addFeeById/{id}")
	public String addEventById(@PathVariable("id") Long id, Fee fee) {

		feeRepo.save(fee);

		return "redirect:/demo/GetFee";

	}

	@GetMapping("/feePage/{id}")
	public String getNoticePage(@PathVariable("id") Long id, Model model) {

		Optional<Fee> userData = feeRepo.findById(id);
		Fee usData = userData.get();
		System.err.println("::::::::::::::userData::::" + userData);
		model.addAttribute("userData", usData);

		return "/admin/pages/notices/noticePage";

	}

	@PostMapping("/addFeeWithSignature")
	public String fileUpload(Fee fee, @RequestParam("file") MultipartFile file, Model model) throws IOException {

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

			fee.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileNameWithRN);
			file.transferTo(fileDirectory);

			fee.setPicUrl(uploadingDir);

			feeRepo.save(fee);

		} else {
			model.addAttribute("success", "Invalie User .!");
			return "redirect:/demo/GetFee";
		}
		model.addAttribute("success", "User Registration Successfully...!");
		return "redirect:/demo/GetFee";
	}

	@PostMapping("/updateFee/{id}")
	public String updateUser(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id, Model model,
			Fee fee, HttpServletRequest request) throws IOException {

		System.err.println(":::::.ExamController.addStudent::::" + fee);

		Optional<Fee> userData = feeRepo.findById(fee.getId());

		Fee userInDB = userData.get();

		System.err.println(":::::.userInDB.userInDB::::" + userInDB.getPhotos());

		if (!file.getOriginalFilename().isEmpty()) {

			System.out.println(":::::.trueeeeeeeeeeeeeeeeeeeeeee::::" + fee);

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
			fee.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);
			fee.setPicUrl(uploadingDir);

			feeService.update(fee);

			return "redirect:/demo/GetFee";

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
			fee.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);
			fee.setPicUrl(uploadingDir);

			feeService.update(fee);

			return "redirect:/demo/GetFee";
		}

	}

}
