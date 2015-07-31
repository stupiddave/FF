package com.dave.fantasyfootball.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.dave.fantasyfootball.config.AppConfig;

public class WebAppInitializer implements WebApplicationInitializer {

	private static Class<?>[] configurationClasses = new Class<?>[] { AppConfig.class };

	@Override
	public void onStartup(ServletContext container) {

		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(configurationClasses);

		container.addListener(new ContextLoaderListener(dispatcherContext));

		ServletRegistration.Dynamic dispatcher = container.addServlet(
				"dispatcher", new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

}
