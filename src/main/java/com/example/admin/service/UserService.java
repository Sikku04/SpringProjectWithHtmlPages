package com.example.admin.service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.admin.entity.AllStudent;
import com.example.admin.entity.ChangePassword;
import com.example.admin.entity.Department;
import com.example.admin.entity.Teacher;
import com.example.admin.entity.User;
import com.example.admin.entity.UserLogin;
import com.example.admin.repository.DepartmentRepo;
import com.example.admin.repository.StudentRepository;
import com.example.admin.repository.TeacherRepo;
import com.example.admin.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService {

	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 1;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository StudentRepo;

	@Autowired
	private TeacherRepo TeacherRepo;
	
	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public Optional<User> userRegistration(User user) {

		String userInDb = user.getEmail();

		Optional<User> optionalUser = userRepository.findByEmail(userInDb);

		if (!optionalUser.isPresent()) {

			return null;
		}

		User userData = new User();

		userData.setName(user.getName());
		userData.setEmail(user.getEmail());
		userData.setPassword(user.getPassword());

		userRepository.save(userData);

		return optionalUser;

	}

	public Optional<User> userLogin(UserLogin userLogin) {

		String emial = userLogin.getEmail();

		Optional<User> optionalUser = userRepository.findByEmail(emial);

		if (!optionalUser.isPresent()) {

			return null;
		}
		User user = optionalUser.get();

		if (userLogin.getEmail().equals(user.getEmail()) && userLogin.getPassword().equals(user.getPassword())
				&& user.isActive() == false) {

			return optionalUser;

		} else {

			return null;
		}

	}

	public User sendSimpleMail(String email) {

		Optional<User> optionalUser = userRepository.findByEmail(email);

		User userData = optionalUser.get();

		userData.setOtp(generateOtp());
		userData.setOtpCreationDate(LocalDateTime.now());

		userRepository.save(userData);

		String otp = userData.getOtp();

		String token = RandomString.make(200);

		String link = "http://localhost:8082/demo/passwordChangePage?token=" + token;

		try {

			sendEmail(email, otp, link);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return userData;

	}

	private String generateOtp() {

		Random random = new Random();
		String otp = String.format("%04d", random.nextInt(9999));
		return otp;
	}

	public void sendEmail(String email, String otp, String link)
			throws MessagingException, UnsupportedEncodingException {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(sender);
		helper.setTo(email);

		String subject = "Heres the link to reset your password";

		helper.setSubject(subject);

		String content = "<p>Dear,  Test </p>"
				+ "<p>You have requested to reset your password. Please click the below link to reset your password</p>"
				+ "<p>Your Otp is " + otp + "</p>" + "<p>Click the link below to change your password:</p>"
				+ "<p><a href=\"" + link + "\" class=\"btn btn-primaryt\">" + link + "</a></p>"

				+ "<p>Ignore this email if you do remember your password, " + "or you have not made the request.</p>"
				+ "<p>Thanks & Regards			DreamSathi Team			Website : - www.myapp.com<br>"
				+ "<div style=\\\"padding: 20px 0; background-color: red; font-size: 14px; margin-left: 300px transition: all 0.3s; border-top: 1px solid #dc3545;\\\"><footer style=\"padding: 20px 0; background-color: #0d6efd4a; font-size: 14px; margin-left: 300px transition: all 0.3s; border-top: 1px solid #dc3545;\">\n"
				+ "<div class=\"copyright\" style=\" text-align: center; color: #012970;\">\n"
				+ " &copy; Copyright <strong><span>DreamSathi</span></strong>. All Rights Reserved\n" + " </div>\n"
				+ " <div class=\"credits\" style=\" padding-top: 5px; text-align: center; font-size: 13px; color: #012970;\">\n"
				+ " \n" + " Designed by <a href=\"https://bootstrapmade.com/\">DreamSathi Team</a>\n" + " </div>\n"
				+ " </footer><!-- End Footer --></div> </p>";

		helper.setText(content, true);

		javaMailSender.send(message);

	}

	public String changePassword(ChangePassword changePassword) {

		String otp = changePassword.getOtp();

		System.out.println(":::::otp::::::" + otp);

		Optional<User> optionalUser = userRepository.findByOtp(otp);

		System.out.println(":::::optionalUser::::::" + optionalUser);

		if (!optionalUser.isPresent()) {

			return "Invalid otp...!!";
		}

		LocalDateTime otpGenerateTime = optionalUser.get().getOtpCreationDate();

		if (isOtpCreatedDateTime(otpGenerateTime)) {

			return "Token expired.";

		}

		if (changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {

			User userData = optionalUser.get();
			userData.setPassword(changePassword.getNewPassword());
			userRepository.save(userData);

		} else {
			return "Your password does not match..!.";
		}

		return "Your password successfully updated.";

	}

	public boolean isOtpCreatedDateTime(LocalDateTime otpCreateTime) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(otpCreateTime, now);

		if (diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES) {
			return true;
		} else {
			return false;
		}

	}

	public Optional<User> getOTP(String otp) {

		Optional<User> optionalUser = userRepository.findByOtp(otp);

		return optionalUser;
	}

	public Optional<User> getEmailId(String email) {

		Optional<User> userOptional = userRepository.findByEmail(email);
		System.out.println("::::::userOptional:::::::::::" + userOptional);

		return userOptional;

	}

	public Optional<User> findByOtp(String otp) {
		Optional<User> optionalUser = userRepository.findByOtp(otp);
		return optionalUser;
	}

	public void update(User user) {

		if (user != null) {

			userRepository.save(user);

		}

	}

	public void updateStudent(AllStudent user) {

		if (user != null) {

			StudentRepo.save(user);

		}

	}

	public void updateTeacher(Teacher teacher) {

		if (teacher != null) {

			TeacherRepo.save(teacher);

		}

	}
	
	public void updateDepartment(Department department) {

		if (department != null) {

			departmentRepo.save(department);

		}

	}
	
	
}
