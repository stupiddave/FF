package com.dave.fantasyfootball.service;

import com.dave.fantasyfootball.domain.User;

public interface UserService {

	void addUser(User newUser);
	User getUserByUsername(String username);
	void buildSessionUser(String name);

}
