package com.dave.fantasyfootball.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.repository.UserRepository;
import com.dave.fantasyfootball.service.UserService;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void addUser(User user) {
		userRepository.addUser(user);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		return user;
	}
}
