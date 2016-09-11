package com.pzy.jcook.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pzy.jcook.workflow.listener.SessionTimeOutListener;

@Configuration
public class ApplicationConfiguration {
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("50MB");
        factory.setMaxRequestSize("50MB");
        return factory.createMultipartConfig();
    }
	@Bean
    public SessionTimeOutListener sessionTimeOutListener() {
        return new SessionTimeOutListener();
    }
}
