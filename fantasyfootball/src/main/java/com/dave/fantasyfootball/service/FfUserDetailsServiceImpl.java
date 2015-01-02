package com.dave.fantasyfootball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.User;

@Service
public class FfUserDetailsServiceImpl implements UserDetailsService {

	UserDetailsBuilder userDetailsBuilder;
	UserService userService;

//	@Autowired
//	User user;

	@Autowired
	public FfUserDetailsServiceImpl(UserService userService,
			UserDetailsBuilder userDetailsBuilder) {
		this.userService = userService;
		this.userDetailsBuilder = userDetailsBuilder;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userService.getUserByUsername(username);
		// user.setUsername(theuser.getUsername());
		// user.setTeamId(theuser.getTeamId());
		// user.setFirstName(theuser.getFirstName());
		// user.setLastName(theuser.getLastName());
		//
		UserDetails userDetails = userDetailsBuilder
				.buildUserDetailsFromUser(user);
		return userDetails;
	}

}
