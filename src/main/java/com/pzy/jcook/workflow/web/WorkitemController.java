package com.pzy.jcook.workflow.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pzy.jcook.dto.json.SuccessResponse;
import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.workflow.entity.Workitem;
import com.pzy.jcook.workflow.service.WorkitemService;
/***
 * 代办已办 签收 等动作
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("workitem")
public class WorkitemController {

	@Autowired
	WorkitemService workitemService;
	
	@Autowired
	private ProcessEngine processEngine;

	@RequestMapping(method=RequestMethod.GET)
	public String create(Model model) {
		return  "workitem/create";
	}
	
	/***
	 * 提交单据并发起流程
	 * @param model
	 * @param workitem
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String create(Model model,Workitem workitem) {
		User user=(User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		workitem.setCreater(user);
		workitemService.save(workitem);
		/**发起流程*/
		Map<String,Object> activtiMap=new HashMap<String,Object>();
		activtiMap.put("title",workitem.getTitle());
		activtiMap.put("creater",user.getId());
		
		ProcessInstance processInstance=processEngine.getRuntimeService().startProcessInstanceByKey("workitem", workitem.getId().toString(),activtiMap);
		/**完成第一步（提交申请）*/
		String sn="WORKITEM"+DateFormatUtils.format(new Date(),"_yyyy_MM_")+processInstance.getId();
		
		processEngine.getRuntimeService().setVariable(processInstance.getProcessInstanceId(), "sn", sn);
		
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
		processEngine.getTaskService().complete(tasks.get(0).getId(),activtiMap);
		
		model.addAttribute("response",new SuccessResponse("单据提交成功！"));
		return  "workflow/create";
	}
	
	
}
