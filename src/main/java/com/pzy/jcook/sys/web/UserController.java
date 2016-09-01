package com.pzy.jcook.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.jcook.dto.json.Response;
import com.pzy.jcook.dto.json.SuccessResponse;
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
	
	@ModelAttribute
	public User preget(@RequestParam(required=false) Long id) {
		if (id!=null){
			return this.getBaseService().find(id);
		}else
			return new User();
	}
}
