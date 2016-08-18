package com.pzy.jcook.sys.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.jcook.dto.json.ObjectResponse;
import com.pzy.jcook.dto.json.Response;
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
	
}
