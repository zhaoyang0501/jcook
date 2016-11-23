package com.pzy.jcook.workflow.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.pzy.jcook.dto.json.DataTableResponse;
import com.pzy.jcook.dto.json.Response;
import com.pzy.jcook.dto.json.SuccessResponse;
import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.service.UserService;
import com.pzy.jcook.sys.shiro.oauth2.OAuth2AuthenticationFilter;
import com.pzy.jcook.workflow.dto.WorkItemDTO;
import com.pzy.jcook.workflow.entity.Workitem;
import com.pzy.jcook.workflow.service.WorkFlowService;
import com.pzy.jcook.workflow.service.WorkitemService;

/***
 * 任务单据的相关操作
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("workitem")
public class WorkitemController {
	private static final Logger log = LoggerFactory.getLogger(WorkitemController.class);
	
	@Autowired
	WorkitemService workitemService;
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WorkFlowService workFlowService;
	@InitBinder  
	protected void initBinder(HttpServletRequest request,  
	            ServletRequestDataBinder binder) throws Exception {   
	      binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}  
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(Model model) {
		log.info("create----------isAuthenticated------------"+SecurityUtils.getSubject().isAuthenticated());
    	 User user = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
    	 log.info("create----------oauth2-----------inde get---"+user);
    	 
		model.addAttribute("users",userService.findAll());
		return  "workitem/create";
	}
	
	@RequestMapping(value="index")
	public String index(Model model) {
		return  "workitem/index";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Response list(Integer start, Integer length, String value) {
		int pageNumber = (int) (start / length) + 1;
		int pageSize = length;
		Page<Workitem> m = workitemService.findAll(pageNumber, pageSize, value,"title");
		List<WorkItemDTO> dtos = new ArrayList<WorkItemDTO>();
		if(CollectionUtils.isNotEmpty(m.getContent())){
			for(Workitem bean:m.getContent()){
				dtos.add(workitemService.converToDto(bean));
			}
		}
		return new DataTableResponse<WorkItemDTO>( dtos,(int) m.getTotalElements());
	}
	/***
	 * 提交单据并发起流程
	 * @param model
	 * @param workitem
	 * @return
	 */
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String create(Model model,Workitem workitem,String applyusers,String filestr) {
		User user=(User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		workitem.setCreater(user);
		if(StringUtils.isNotBlank(filestr))
			workitem.setFiles(filestr.split(","));
		workitem.setCreateDate(new Date());
		workitemService.save(workitem,applyusers);
		/**发起流程*/
		Map<String,Object> activtiMap=new HashMap<String,Object>();
		activtiMap.put("title",workitem.getTitle());
		activtiMap.put("creater",user.getId());
		
		List<User> users = userService.findUserByRole(2l);
		if(users.size()<1){
			throw new RuntimeException("系统没有设置管理员");
		}
		activtiMap.put("divider",users.get(0).getId());
		
		ProcessInstance processInstance=processEngine.getRuntimeService().startProcessInstanceByKey("workitem", workitem.getId().toString(),activtiMap);
		/**完成第一步（提交申请）*/
		String sn="WORKITEM"+DateFormatUtils.format(new Date(),"_yyyy_MM_")+processInstance.getId();
		
		processEngine.getRuntimeService().setVariable(processInstance.getProcessInstanceId(), "sn", sn);
		
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
		processEngine.getTaskService().complete(tasks.get(0).getId(),activtiMap);
		
		model.addAttribute("response",new SuccessResponse("单据提交成功！"));
		model.addAttribute("users",userService.findAll());
		return  "workitem/create";
	}
	/***
	 * 跳转到表单详情
	 * @param taskid
	 * @param prcessInstanceid
	 * @return
	 */
	@RequestMapping("/task/{taskid}/{prcessInstanceid}")
	public ModelAndView task(@PathVariable String taskid,@PathVariable String prcessInstanceid) {
		ProcessInstance processInstance=processEngine.getRuntimeService().
				createProcessInstanceQuery().processInstanceId(prcessInstanceid).singleResult();
		Task task = processEngine.getTaskService().createTaskQuery().taskId(taskid).singleResult();
		/**找task key用于页面跳转*/
		String taskkey="";
		
		if(task==null){
			HistoricTaskInstance historicTaskInstance = processEngine.getHistoryService().
					createHistoricTaskInstanceQuery().taskId(taskid).singleResult();
			
			taskkey = historicTaskInstance.getTaskDefinitionKey();	
		}else{
			taskkey=task.getTaskDefinitionKey();;
		}
			
		String businessKey ="";
		if(processInstance==null) 
			businessKey=processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(prcessInstanceid).singleResult().getBusinessKey();
		else
			businessKey = processInstance.getBusinessKey();
		
		ModelAndView  mav = new ModelAndView("workitem/"+taskkey);
		
		/**task ,assignee，prcessInstanceid，processDefinitionId用于审批页面顶部提示以及流程图显示*/
		Task currentTask=workFlowService.getCurrentTask(prcessInstanceid);
		mav.addObject("task",currentTask);
		mav.addObject("processDefinitionId", processInstance.getProcessDefinitionId());
		mav.addObject("taskhistory", workFlowService.findHistoryTask(prcessInstanceid));
		mav.addObject("prcessInstanceid", prcessInstanceid);
		mav.addObject("users",userService.findAll());
		mav.addObject("bean", workitemService.find(Long.valueOf(businessKey)));
		mav.addObject("sn", processEngine.getRuntimeService().getVariable(prcessInstanceid, "sn"));
		return mav;
	}
	
	@RequestMapping("/taskhistory/{prcessInstanceid}")
	public ModelAndView taskhistory(@PathVariable String prcessInstanceid) {
		HistoricProcessInstance processInstance = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(prcessInstanceid).singleResult();
		Assert.notNull(processInstance, "参数错误或者流程不存在");
		String bussnessid = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(prcessInstanceid).singleResult().getBusinessKey();

		ModelAndView mav = new ModelAndView("workitem/history");
		mav.addObject("taskhistory", workFlowService.findHistoryTask(prcessInstanceid));
		mav.addObject("bean", workitemService.find(Long.valueOf(bussnessid)));
		mav.addObject("sn", processEngine.getHistoryService().createHistoricVariableInstanceQuery().processInstanceId(prcessInstanceid).variableName("sn").singleResult().getValue().toString());
		/** task ,assignee，prcessInstanceid，processDefinitionId用于审批页面顶部提示以及流程图显示 */
		Task currentTask = workFlowService.getCurrentTask(prcessInstanceid);
		mav.addObject("task", currentTask);
		mav.addObject("prcessInstanceid", prcessInstanceid);
		mav.addObject("processDefinitionId", processInstance.getProcessDefinitionId());
		return mav;
	}
	
	@RequestMapping("/doapprove/{taskid}/{prcessInstanceid}")
	public String doapprove(@PathVariable String taskid,@PathVariable String prcessInstanceid,
								Boolean pass,String approvals,
								Workitem workitem,
								String handleusers,
								String filestr,
								RedirectAttributes redirectAttributes ) {
		ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(prcessInstanceid).singleResult();
		Task task = processEngine.getTaskService().createTaskQuery().taskId(taskid).singleResult();
		Map<String, Object> activtiMap = new HashMap<String, Object>();
		
		/** TODO 判断当前登录人是不是任务的拥有者 ***/
		if ("divide".equals(task.getTaskDefinitionKey())) {
			if(StringUtils.isBlank(handleusers))
				throw new RuntimeException("没有选择处理人");
			List<String> assigneeList = new ArrayList<String>();
			Collections.addAll(assigneeList, handleusers.split(","));
			activtiMap.put("assigneeList", assigneeList);
			activtiMap.put("pass", pass);
			processEngine.getTaskService().addComment(task.getId(), processInstance.getId(), approvals);
			processEngine.getTaskService().complete(task.getId(), activtiMap);
			workitem.setApprove(approvals);
			if(workitem.getLeader()!=null&workitem.getLeader().getId()!=null)
				workitem.setLeader(userService.find(workitem.getLeader().getId()));
			workitem.setReject(pass?0:1);
			this.workitemService.save(workitem,handleusers);
		} else if ("handle".equals(task.getTaskDefinitionKey())) {
			/**上传附件*/
			if(StringUtils.isNotBlank(filestr)){
				for(String str:filestr.split(",")){
					processEngine.getTaskService().saveAttachment(processEngine.getTaskService().createAttachment("file", task.getId(), processInstance.getId(), str, str, str));
				}
			}
			
			processEngine.getTaskService().addComment(task.getId(), processInstance.getId(), approvals);
			processEngine.getTaskService().complete(task.getId(), activtiMap);
			this.workitemService.save(workitem);
		}
	
		redirectAttributes.addFlashAttribute("response", new SuccessResponse("操作成功"));
		return "redirect:/workitem/taskhistory/" + prcessInstanceid;
	}
	
	@ModelAttribute
	public Workitem preget(@RequestParam(required=false) Long id) {
		Workitem workitem = new Workitem();
		if (id!=null){
			workitem = this.workitemService.find(id);
		}
		return workitem;
	}
}
