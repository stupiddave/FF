package com.dave.fantasyfootball.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.service.UserDetailsBuilder;

@Service
public class UserDetailsBuilderImpl implements UserDetailsBuilder {

	@Override
	public UserDetails buildUserDetailsFromUser(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getUserType()));

		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				username, password, authorities);
		return userDetails;
	}

}
