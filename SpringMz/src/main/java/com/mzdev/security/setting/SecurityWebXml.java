package com.mzdev.security.setting;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class SecurityWebXml extends AbstractSecurityWebApplicationInitializer{

	protected void beforeSpringSecurityFilterChain(ServletContext servletContext){
		
		super.insertFilters(servletContext, getCharacterEncodingFilter());
		
		AnnotationConfigWebApplicationContext mzContext = new AnnotationConfigWebApplicationContext();
		mzContext.register(ServletContextXml.class);
		ContextLoaderListener listener = new ContextLoaderListener(mzContext);
		servletContext.addListener(listener);
		mzContext.setServletContext(servletContext);
		
		DispatcherServlet dispatcher = new DispatcherServlet(mzContext);
		
		ServletRegistration.Dynamic mzServlet = servletContext.addServlet("dispatcher", dispatcher);
		
		mzServlet.addMapping("/");
		mzServlet.setLoadOnStartup(1);
		
	}
	
	private CharacterEncodingFilter getCharacterEncodingFilter(){
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		
		return filter;
	}
	
}
