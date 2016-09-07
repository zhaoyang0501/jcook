package com.pzy.jcook.workflow.listener;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.service.UserService;
import com.pzy.jcook.workflow.service.nofy.NofyService;

/***
 发送短消息提示
 * 监听 "TASK_ASSIGNED"事件
 * @author panchaoyang
 *
 */
@Service
public class TaskAssignedListener implements ActivitiEventListener {
	
	private static final Logger log = LoggerFactory.getLogger(TaskAssignedListener.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NofyService nofyService;
	
	@Override
	public void onEvent(ActivitiEvent event) {
		
		Object entity = ((ActivitiEntityEvent)event).getEntity();
		if(entity instanceof TaskEntity){
			TaskEntity taskEntity = (TaskEntity)entity;
			ProcessInstance processInstance = event.getEngineServices().getRuntimeService().createProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult();
			if(processInstance==null)
				return ;
			String taskname = (String) taskEntity.getVariable("title");
			User user = userService.find(Long.parseLong(taskEntity.getAssignee()));
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("taskname", taskname);
			map.put("username", user.getChinesename());
			map.put("phone", user.getUsername());
			log.info("任务监听：{}收到一条待办任务，taskid{}",user.getChinesename(),taskEntity.getId());
			nofyService.send(map);
		}
		
		
	}

	@Override
	public boolean isFailOnException() {
		return false;
	}

}
