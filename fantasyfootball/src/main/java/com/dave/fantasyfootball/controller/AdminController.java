package com.dave.fantasyfootball.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dave.fantasyfootball.service.PropertiesService;
import com.dave.fantasyfootball.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	PropertiesService propertiesService;
	private UserService userService;

	@Autowired
	public AdminController(PropertiesService propertiesService,
			UserService userService) {
		this.propertiesService = propertiesService;
		this.userService = userService;
	}

	@RequestMapping
	public String admin(Model model, HttpServletRequest request) {
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", userService.getUserByUsername(request
					.getUserPrincipal().getName()));
		}
		model.addAttribute("currentGameweek",
				propertiesService.getCurrentGameweek());

		return "admin";
	}

	@RequestMapping(value = "/incrementGameweek")
	public String incrementGameweek() {
		propertiesService.incrementGameweek();
		return "redirect:/admin";
	}

	@RequestMapping(value = "/decrementGameweek")
	public String decrementGameweek() {
		propertiesService.decrementGameweek();
		return "redirect:/admin";
	}
}
