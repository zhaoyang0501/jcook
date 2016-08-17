package com.pzy.jcook.sys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	protected com.pzy.jcook.sys.service.UserService baseService;
	
	
	UserRepository userRepository;
	
	@RequestMapping("/fuckyou")
	public String index(){
		baseService.save(new User());
		userRepository.findAll();
		return "fuckyou";
	}
	@RequestMapping("/login")
	public String login(){
		userRepository.findAll();
		return "login";
	}
}
