package com.pzy.jcook.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.service.UserService;

@Controller
@RequestMapping("user")
public class UserController extends AbstractBaseCURDController<User,Long>  {
	
	@Override
	public UserService getBaseService() {
		return (UserService)super.getBaseService();
	}
	
	@Override
	String getBasePath() {
		return "user";
	}
	@RequestMapping("saveUser")
	public ModelAndView saveUser(User user){
		ModelAndView mv = new ModelAndView("/index");
		return mv;
	}
	
	
}
