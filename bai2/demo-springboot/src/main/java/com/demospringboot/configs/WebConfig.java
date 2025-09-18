package com.demospringboot.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver clr = new CookieLocaleResolver();
        clr.setCookieName("Locale"); // cookie name sẽ là "Locale"
        clr.setDefaultLocale(Locale.ENGLISH); // mặc định EN
        clr.setCookieMaxAge(60 * 60 * 24 * 30); // 30 ngày
        return clr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // sẽ nhận param ?lang=vi hoặc ?lang=en
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/static/**")
                .addResourceLocations("/resources/static");

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + "C:/codegym/module4/bai2/demo-springboot/src/main/resources/uploads/")
                .setCachePeriod(0);
    }

}
