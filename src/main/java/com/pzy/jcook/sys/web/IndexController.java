package com.pzy.jcook.sys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pzy.jcook.sys.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping("/fuckyou")
	public String index(){
		userRepository.findAll();
		return "fuckyou";
	}
	@RequestMapping("/login")
	public String login(){
		userRepository.findAll();
		return "login";
	}
}
