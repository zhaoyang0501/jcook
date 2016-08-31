package com.pzy.jcook.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("workitem")
public class WorkitemControler {
	
	@RequestMapping("create")
	public String  create(){
		return "/workitem/create";
	}
}
