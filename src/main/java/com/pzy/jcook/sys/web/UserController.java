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
	
	@RequestMapping("listall")
	@ResponseBody
	public Map listall(Integer start, Integer length, String name) {
		int pageNumber = (int) (start / length) + 1;
		int pageSize = length;
		Page<User> m = getBaseService().findAll(pageNumber, pageSize, name,"username");
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", m.getContent());
		map.put("recordsTotal", 2323);
		map.put("recordsFiltered", m.getTotalElements());
		map.put("draw", 1);
		return map;
	}
}
