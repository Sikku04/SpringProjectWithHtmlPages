package com.example.admin.contoller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.admin.entity.AllStudent;
import com.example.admin.entity.ChangePassword;
import com.example.admin.entity.User;
import com.example.admin.entity.UserLogin;
import com.example.admin.repository.StudentRepository;
import com.example.admin.repository.UserRepository;
import com.example.admin.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository stutRepo;

	// Registration API

	@GetMapping("/test")
	public String test() {
		return "success";
	}

	@PostMapping("/signUp")
	public Optional<User> userRegistration(User user) {
		System.out.println(":::::::User::::::" + user);

		Optional<User> response = userService.userRegistration(user);

		return response;

	}

	// Login API

	@PostMapping("/login")
	public Optional<User> userLogin(UserLogin userLogin, Model model) {

		System.out.println(":::::::userLogin::::::" + userLogin);

		Optional<User> response = userService.userLogin(userLogin);

		model.addAttribute("response", response);

		return response;

	}

	// Send Email API

	// ForgotPassword API

	@PostMapping("/sendEmail/{email}")
	public User sendMail(@PathVariable("email") String emial) {

		System.out.println(":::::emial::::::" + emial);

		User response = userService.sendSimpleMail(emial);

		return response;

	}

	// ChangePassword API

	@PostMapping("/changePassword")
	public String changePassword(ChangePassword changePassword) {

		System.out.println(":::::<><>ChangePassword<><><::::::" + changePassword);

		String response = userService.changePassword(changePassword);

		return response;

	}

	@GetMapping("/getEmailId/{email}")
	public Optional<User> getEmailId(@PathVariable("email") String email) {

		Optional<User> response = userService.getEmailId(email);

		return response;
	}

	@GetMapping("/getOtp/{otp}")
	public Optional<User> getUserOtp(@PathVariable("otp") String otp) {

		Optional<User> response = userService.findByOtp(otp);

		return response;
	}

	@RequestMapping("/getAllRecords")
	public List<User> getAllRecords() {

		List<User> response = userRepository.findAll();

		return response;

	}

	@GetMapping("/getByClassName/{standard}")
	public List<AllStudent> getByClassName(@PathVariable("standard") String standard) {

		List<AllStudent> response = stutRepo.findByStandard(standard);

		return response;
	}

	@RequestMapping("/getAllStudent")
	public List<AllStudent> getAllStudents() {

		List<AllStudent> response = stutRepo.findAll();

		return response;

	}

}
