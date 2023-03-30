package com.example.admin.contoller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.admin.entity.Department;
import com.example.admin.entity.Notification;
import com.example.admin.repository.DepartmentRepo;
import com.example.admin.repository.NotificationRepo;
import com.example.admin.util.Constant;
import com.example.admin.util.EnumNotification;

@Controller
@RequestMapping("/demo")
public class DepartmentController {

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private DepartmentRepo departmentrepo;

	// ::::::::::::::::::::::::::::::Start Department ::::::::::::::::::::;;
	// ::::::::::::::::::::::::::::::Start Department ::::::::::::::::::::;;

	@GetMapping("/GetDeparetment")
	public String GetDeparetment(Model model) {

		List<Department> response = departmentrepo.findAll();

		System.out.println(":::::::::::response::::::::::" + response);

		model.addAttribute("UserDataFromDb", response);

		return "/admin/pages/department";

	}

	@GetMapping("/addNewDepartment")
	public String addNewDepartment() {

		return "admin/addDepartment";

	}

	@GetMapping("/editDepartment/{id}")
	public String getEditDepartment(@PathVariable("id") Long id, Model model) {

		Optional<Department> userData = departmentrepo.findById(id);
		Department usData = userData.get();
		System.err.println("::::::::::::::userData::::" + userData);
		model.addAttribute("userData", usData);

		return "admin/editDepartment";

	}

	@GetMapping("/deleteDepartment/{id}")
	public String getDeleteDepartment(@PathVariable("id") Long id) {

		departmentrepo.deleteById(id);

		return "redirect:/demo/GetDeparetment";

	}

	@PostMapping("/addDepartment")
	public String userRegistration(Department department) {
		System.out.println(":::::::department::::::" + department);

		departmentrepo.save(department);

		System.out.println(":::::department:::::::" + department.getDeptId());

		Notification obj = new Notification(null, null, department.getDeptId(),
				"A department has been signup " + department.getDeptName(), Constant.getDateAndTime(),
				Constant.getDateAndTime(), EnumNotification.Staff_SIGNUP);

		notificationRepo.save(obj);

		return "redirect:/demo/GetDeparetment";

	}

	@PostMapping("/addDepartmentById/{id}")
	public String addDepartmentById(@PathVariable("id") Long id, Department department) {

		departmentrepo.save(department);

		return "redirect:/demo/GetDeparetment";

	}

	// ::::::::::::::::::::::::::::::End Department ::::::::::::::::::::;;
	// ::::::::::::::::::::::::::::::End Department ::::::::::::::::::::;;

}
