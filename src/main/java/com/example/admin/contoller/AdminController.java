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

import com.example.admin.entity.User;
import com.example.admin.repository.UserRepository;
import com.example.admin.service.UserService;
import com.example.admin.util.Constant;

@Controller
@RequestMapping("/demo")
public class AdminController {

	private static final String uploadingDir = Constant.IMG_LOCATION;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@GetMapping("/dashboard")
	public String getDeshboard() {

		return "admin/dashboard";

	}

	@GetMapping("/home")
	public String getHome() {

		return "admin/home";

	}

	@GetMapping("/index")
	public String getLogin() {

		return "admin/index";

	}

	@GetMapping("/getContect")
	public String getContect() {

		return "admin/contect";

	}
	
	

	@GetMapping("/GetDemo")
	public String GetDemo() {

		return "admin/Demo";

	}

	@GetMapping("/forgotPage")
	public String getForgotPage() {

		return "admin/forgotPasswordPage";
	}

	@GetMapping("/passwordChangePage")
	public String getPasswordChangePage() {

		return "admin/ChangePasswordPage";
	}

	// ::::::::::::::::::::Start User ::::::::::::::::::::::
	// ::::::::::::::::::::Start User ::::::::::::::::::::::

	// Handling file upload request
	@PostMapping("/SignUpWithImage")
	public String fileUpload(User user, @RequestParam("file") MultipartFile file, Model model) throws IOException {

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

			user.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileNameWithRN);
			file.transferTo(fileDirectory);

			user.setPicUrl(uploadingDir);

			userRepository.save(user);

		} else {
			model.addAttribute("success", "Invalie User .!");
			return "admin/index";
		}
		model.addAttribute("success", "User Registration Successfully...!");
		return "admin/index";
	}

	@GetMapping("/editProfile/{userId}")
	public String getUserProfile(@PathVariable("userId") Long userId, Model model) {

		Optional<User> userData = userRepository.findById(userId);
		User usData = userData.get();
		System.err.println("::::::::::::::userData" + userData);
		model.addAttribute("userData", usData);

		return "admin/userProfile";

	}

	@PostMapping("/updateUser/{id}")
	public String updateUser(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id, Model model,
			User user, HttpServletRequest request) throws IOException {

		
		Optional<User> userData = userRepository.findById(user.getId());

		User userInDB = userData.get();

		
		if (!file.getOriginalFilename().isEmpty()) {

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
			user.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);

			userService.update(user);

			return "redirect:/demo/editProfile/{id}";

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
			user.setPhotos(imagepathurl);
			File fileDirectory = new File(uploadingDir + fileName);
			file.transferTo(fileDirectory);

			userService.update(user);

			return "redirect:/demo/editProfile/{id}";
		}

	}

	// ::::::::::::::::::::End User ::::::::::::::::::::::
	// ::::::::::::::::::::End User ::::::::::::::::::::::

}
