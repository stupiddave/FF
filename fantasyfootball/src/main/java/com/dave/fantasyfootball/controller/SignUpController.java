package com.dave.fantasyfootball.controller;

import org.springframework.stereotype.Controller;

@Controller
public class SignUpController {

	// @RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String signUp() {
		return "signUp";
	}

}
