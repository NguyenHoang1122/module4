package com.casemodule4.configs;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{HibernateConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class}; // Use WebConfig for servlet context
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"}; // Map all requests to the DispatcherServlet
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        String tmpDir = System.getProperty("java.io.tmpdir");
        registration.setMultipartConfig(new MultipartConfigElement(tmpDir));
        System.out.println(tmpDir);
    }

}