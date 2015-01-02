package com.dave.fantasyfootball.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.dave.fantasyfootball.domain.User;

public interface UserDetailsBuilder {

	public UserDetails buildUserDetailsFromUser(User user);
}
