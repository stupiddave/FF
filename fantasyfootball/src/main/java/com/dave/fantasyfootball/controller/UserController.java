package com.dave.fantasyfootball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dave.fantasyfootball.service.UserService;

@Controller
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

}
