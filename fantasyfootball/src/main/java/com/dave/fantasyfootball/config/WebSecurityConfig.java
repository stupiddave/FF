package com.dave.fantasyfootball.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import com.dave.fantasyfootball.service.impl.UserServiceImpl;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	@Autowired
	UserServiceImpl userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
		// auth.jdbcAuthentication()
		// .dataSource(dataSource)
		// .usersByUsernameQuery(
		// "SELECT username, password, 'true' FROM user_t WHERE username = ?")
		// .authoritiesByUsernameQuery(
		// "SELECT username, user_type FROM user_t WHERE username = ?");
		// .inMemoryAuthentication()
		// .withUser("user").password("password").roles("USER");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/signUp", "/webjars/**")
			.permitAll()
			.antMatchers("/admin/**")
			.hasAuthority("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
			.logout()
			.permitAll();
	}

}
