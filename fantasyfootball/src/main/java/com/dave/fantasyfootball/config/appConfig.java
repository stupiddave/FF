package com.dave.fantasyfootball.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.dave.fantasyfootball.controller.TeamController;
import com.dave.fantasyfootball.repository.SelectionRepository;
import com.dave.fantasyfootball.repository.SelectionRepositoryImpl;
import com.dave.fantasyfootball.service.PropertiesService;
import com.dave.fantasyfootball.service.PropertiesServiceImpl;
import com.dave.fantasyfootball.service.SelectionService;
import com.dave.fantasyfootball.service.SelectionServiceImpl;
import com.dave.fantasyfootball.service.TeamService;
import com.dave.fantasyfootball.service.TeamServiceImpl;

@EnableWebMvc
@ComponentScan(basePackages = { "com.dave.fantasyfootball" })
@Configuration
public class appConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
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
		templateEngine.setTemplateResolver(this
				.getServletContextTemplateResolver());
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver getThymeleafViewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(this.getTemplateEngine());
		return viewResolver;
	}

	@Bean
	public TeamService getTeamService() {
		TeamService teamService = new TeamServiceImpl();
		return teamService;
	}
	
	@Bean
	public PropertiesService getPropertiesService() {
		PropertiesService propetiesService = new PropertiesServiceImpl();
		return propetiesService;
	}
	
	@Bean SelectionRepository getSelectionRepository() {
		SelectionRepository selectionRepository = new SelectionRepositoryImpl();
		return selectionRepository;
	}
	
	@Bean 
	SelectionService getSelectionService() {
		SelectionService selectionService = new SelectionServiceImpl();
//		selectionService.
		return selectionService;
	}

//	@Bean
//	public TeamController getTeamController() {
//		TeamController teamController = new TeamController();
//		return teamController;
//	}
}