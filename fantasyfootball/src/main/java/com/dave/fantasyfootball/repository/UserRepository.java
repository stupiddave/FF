package com.dave.fantasyfootball.repository;

import com.dave.fantasyfootball.domain.User;

public interface UserRepository {

	void addUser(User user);
	User getUserByUsername(String username);
}
