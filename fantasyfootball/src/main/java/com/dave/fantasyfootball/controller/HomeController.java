package com.dave.fantasyfootball.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.service.TeamService;
import com.dave.fantasyfootball.service.UserService;

@Controller
public class HomeController {

	private UserService userService;
	private TeamService teamService;

	@Autowired
	private User user;

	@Autowired
	public HomeController(UserService userService, TeamService teamService) {
		this.userService = userService;
		this.teamService = teamService;
		// this.user = user;
	}

	@RequestMapping("/")
	public String welcome(Model model, HttpServletRequest request) throws MalformedURLException, JSONException, IOException {
		if (user.getUsername() == null) {
			userService.buildSessionUser(request.getUserPrincipal()
					.getName());
		}
		
//		Team team = teamService.getTeamById(user.getTeamId());

		if (!model.containsAttribute("user")) {
			model.addAttribute("user", user);
		}
//		if (!model.containsAttribute("team")) {
//			model.addAttribute("team", team);
//		}
		return "home";
	}

	@RequestMapping("/login")
	public String login() {

		return "login";

	}
}
