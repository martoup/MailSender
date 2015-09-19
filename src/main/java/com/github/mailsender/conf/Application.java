package com.github.mailsender.conf;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * No web.xml spring application configuration. This class creates application
 * context and dispatcher servlet.
 * 
 * @author Martin Nikolov
 *
 */
@ComponentScan
@EnableWebMvc
class Application implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(Application.class);
		appContext.setServletContext(servletContext);
		appContext.refresh();

		Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(appContext));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
	}
}
