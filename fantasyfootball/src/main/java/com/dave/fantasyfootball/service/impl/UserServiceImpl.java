package com.dave.fantasyfootball.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.repository.UserRepository;
import com.dave.fantasyfootball.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	@Autowired
	User user;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void addUser(User user) {
		userRepository.addUser(user);
	}

	@Override
	public User getUserByUsername(String username) {
		User user = userRepository.getUserByUsername(username);
		return user;
	}

	@Override
	public void buildSessionUser(String username) {
		User currentUser = getUserByUsername(username);
		user.setUsername(username);
		user.setFirstName(currentUser.getFirstName());
		user.setLastName(currentUser.getLastName());
		user.setEmail(currentUser.getEmail());
		user.setTeamId(currentUser.getTeamId());
		user.setUserType(currentUser.getUserType());
	}

}
