package com.dave.fantasyfootball.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dave.fantasyfootball.domain.User;

@Controller
public class HomeController {

	@RequestMapping(value = { "/", "/home" })
	public String welcome(Model model, HttpServletRequest request, @AuthenticationPrincipal User user)
			throws MalformedURLException, JSONException, IOException {
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", user);
		}
		return "home";
	}

	@RequestMapping("/login")
	public String login() {

		return "login";

	}
}
