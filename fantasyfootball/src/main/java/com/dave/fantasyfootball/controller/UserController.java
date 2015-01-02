package com.dave.fantasyfootball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.service.UserService;

@Controller
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService){
		this.userService = userService;
	}
	
	@RequestMapping(value="/addUser", method=RequestMethod.GET)
	public String addUserForm(@ModelAttribute("newUser") User newUser) {
		return "addUser";
	}
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public String processNewUser(@ModelAttribute("newUser") User newUser) {
		userService.addUser(newUser);
		return "redirect:/";
	}

}
