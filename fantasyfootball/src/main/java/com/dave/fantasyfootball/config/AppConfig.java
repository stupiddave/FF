package com.dave.fantasyfootball.config;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.mysql.jdbc.Driver;

@EnableWebMvc
@ComponentScan(basePackages = { "com.dave.fantasyfootball" })
@Configuration
@PropertySource("classpath:/properties/application.properties")
// @EnableAspectJAutoProxy
public class AppConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("/webjars/");
	}

	@Bean
	public ServletContextTemplateResolver getServletContextTemplateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine getTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(this.getServletContextTemplateResolver());
		Set<IDialect> dialects = new HashSet<IDialect>();
		dialects.add(getSpringSecurityDialect());
		templateEngine.setAdditionalDialects(dialects);
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver getThymeleafViewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(this.getTemplateEngine());
		return viewResolver;
	}

	@Bean
	public SpringSecurityDialect getSpringSecurityDialect() {
		SpringSecurityDialect dialect = new SpringSecurityDialect();
		return dialect;
	}

	@Bean
	static PropertySourcesPlaceholderConfigurer configurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${db.connection.url}")
	String url;

	@Value("${db.username}")
	String username;

	@Value("${db.password}")
	String password;

	@Bean
	NamedParameterJdbcTemplate getJdbcTemplate() throws SQLException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		return jdbcTemplate;
	}

	@Bean
	Driver getDriver() throws SQLException {
		Driver driver = new Driver();
		return driver;
	}

	@Bean
	DataSource getDataSource() throws SQLException {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource(getDriver(), url, username, password);
		System.out.println(dataSource.getUrl());
		return dataSource;
	}

}