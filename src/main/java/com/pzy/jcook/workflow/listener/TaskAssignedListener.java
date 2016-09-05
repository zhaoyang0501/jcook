package com.pzy.jcook.workflow.listener;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.service.UserService;
/***
 发送短消息提示
 * 监听 "TASK_ASSIGNED"事件
 * @author panchaoyang
 *
 */
public class TaskAssignedListener implements ActivitiEventListener {
	
	private static final Logger log = LoggerFactory.getLogger(TaskAssignedListener.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	public void onEvent(ActivitiEvent event) {
		
		ActivitiEntityEvent ActivitiEntityEvent =(ActivitiEntityEvent)event;
		Object entity = ActivitiEntityEvent.getEntity();
		
		if(entity instanceof TaskEntity){
			TaskEntity taskEntity = (TaskEntity)entity;
			ProcessInstance processInstance = event.getEngineServices().getRuntimeService().createProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult();
			
			if(processInstance==null)
				return ;
			Long Assigneeid = Long.parseLong(taskEntity.getAssignee());
			User user = userService.find(Assigneeid);
			//发送短息逻辑
		}
		
		
	}

	@Override
	public boolean isFailOnException() {
		return false;
	}

}
