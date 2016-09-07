package com.pzy.jcook.workflow.service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.repository.UserRepository;
import com.pzy.jcook.sys.service.BaseService;
import com.pzy.jcook.workflow.dto.WorkItemDTO;
import com.pzy.jcook.workflow.entity.Workitem;

@Service
public class WorkitemService extends BaseService<Workitem, Long> {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProcessEngine processEngine;
	
	/***
	 * 连同推荐组员一起保存
	 * @param workitem
	 * @param applyuserids
	 */
	public void save(Workitem workitem,String applyuserids){
		if(StringUtils.isNotBlank(applyuserids)){
			Set<User> users = new HashSet<User>();
			String[] userids = applyuserids.split(",");
			for(String id: userids){
				users.add(userRepository.findOne(Long.valueOf(id)));
			}
			workitem.setApplyusers(users);
		}
		this.baseRepository.save(workitem);
	}
	
	public WorkItemDTO converToDto(Workitem workitem){
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceBusinessKey(String.valueOf(workitem.getId())).orderByTaskCreateTime().desc().list();
		WorkItemDTO dto = new WorkItemDTO(workitem);
		if(CollectionUtils.isNotEmpty(tasks)){
			dto.setStep(tasks.get(0).getName());
		}
		ProcessInstance processInstance=processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(workitem.getId())).singleResult();
		if(processInstance!=null){
			dto.setProcessInstanceId(processInstance.getId());
			dto.setState(WorkItemDTO.STATE_RUNING);
		}
		
		HistoricProcessInstance p = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(workitem.getId())).singleResult();
		if(p!=null&&p.getEndTime()!=null){
			dto.setProcessInstanceId(p.getId());
			dto.setEndDate(p.getEndTime());
			dto.setState(WorkItemDTO.STATE_END);
		}
			
		return dto;
	}
	
	/***
	 * 连同处理人员一起保存
	 * @param workitem
	 * @param applyuserids
	 */
	public void saveHanderUser(Workitem workitem,String applyuserids){

		if(StringUtils.isNotBlank(applyuserids)){
			Set<User> users = new HashSet<User>();
			String[] userids = applyuserids.split(",");
			for(String id: userids){
				users.add(userRepository.findOne(Long.valueOf(id)));
			}
			workitem.setHandleusers(users);
		}
		this.baseRepository.save(workitem);
	}
	
}
