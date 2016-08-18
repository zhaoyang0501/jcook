package com.pzy.jcook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.pzy.jcook.sys.repository")
@ImportResource( {"spring/applicationContext-shiro.xml"} ) 
public class SpringBootContext extends SpringBootServletInitializer {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootContext.class);
	}
	public static void main(String args[]){
		SpringApplication.run(SpringBootContext.class, args);
	}
}
