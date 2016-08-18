package com.pzy.jcook.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pzy.jcook.sys.entity.User;

@Controller
@RequestMapping("user")
public class UserController extends AbstractBaseCURDController<User,Long>  {
	
	@Override
	String getBasePath() {
		return "user";
	}
	
	
}
