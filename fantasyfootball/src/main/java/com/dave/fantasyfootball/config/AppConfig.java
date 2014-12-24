package com.dave.fantasyfootball.config;

import javax.sql.DataSource;

import org.apache.derby.jdbc.ClientDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@EnableWebMvc
@ComponentScan(basePackages = { "com.dave.fantasyfootball" })
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth)
//			throws Exception {
//		auth.inMemoryAuthentication().withUser("user1").password("password")
//				.roles("USER");
//	}
//
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//		.antMatcher("/*");
//	}

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
	NamedParameterJdbcTemplate getJdbcTemplate() {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				getDataSource());
		return jdbcTemplate;
	}

	@Bean
	ClientDriver getDriver() {
		ClientDriver driver = new ClientDriver();
		return driver;
	}

	@Bean
	DataSource getDataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource(
				getDriver(), "jdbc:derby://localhost:1527/FF_DB;create=true");
		System.out.println(dataSource.getUrl());
		return dataSource;
	}
}