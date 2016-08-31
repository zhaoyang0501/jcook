package com.pzy.jcook.workflow.web;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("workflow")
public class WorkFlowController {
	 
	 @Autowired
	 private RuntimeService runtimeService;
	 
	 @RequestMapping("tasktodo")
	 public String tasktodo(){
		 runtimeService.activateProcessInstanceById("1");
		 return "";
	 }
}
