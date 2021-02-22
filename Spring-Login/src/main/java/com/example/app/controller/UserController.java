package com.example.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.entity.User;
import com.example.app.repository.RoleRepository;
import com.example.app.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/userForm")
	public String userForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roleList", roleRepository.findAll());
		model.addAttribute("listTab", "active");
		return "user-form/user-view";
	}

	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm") User user, BindingResult bindingResult,
			ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab", "active");
		} else {
			try {
				userService.createUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab", "active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab", "active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roleList", roleRepository.findAll());
			}
		}
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roleList", roleRepository.findAll());
		return "user-form/user-view";
	}

}
