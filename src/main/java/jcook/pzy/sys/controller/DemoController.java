package jcook.pzy.sys.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoController {

	@RequestMapping("/")
	String index(){
		return "hello";
	}
	
	public static void main(String arg[]){
		SpringApplication.run(DemoController.class, arg);
	}
}
