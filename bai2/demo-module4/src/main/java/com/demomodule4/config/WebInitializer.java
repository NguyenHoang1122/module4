package com.demomodule4.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null; // Không có cấu hình root
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class}; // Nạp cấu hình Web MVC
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"}; // Map toàn bộ request vào DispatcherServlet
    }

}