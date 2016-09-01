package com.pzy.jcook.workflow.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.repository.UserRepository;
import com.pzy.jcook.workflow.dto.ActivitDTO;


@Service
public class WorkFlowService {
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private UserRepository userRepository;
	
	/***
	 * 查询待办事项
	 * @param begin
	 * @param end
	 * @param creater
	 * @param sn
	 * @param title
	 * @param flow
	 * @param owner
	 * @param firstResult
	 * @param pageSize
	 * @return
	 * @throws ParseException
	 */
	public Page<ActivitDTO> findTaskTodo( final Date begin, final Date end, final Long creater,
			 final String sn, final String title, final String flow,Long owner,Integer firstResult,Integer pageSize) throws ParseException{
		TaskQuery taskQuery=processEngine.getTaskService().createTaskQuery().taskCandidateOrAssigned(String.valueOf(owner));
		if(begin!=null)	
			taskQuery=taskQuery.taskCreatedAfter(begin);
		
		if(end!=null)
			taskQuery=taskQuery.taskCreatedBefore(DateUtils.addDays(end, 1));
		
		if(creater!=null)
			taskQuery=taskQuery.processVariableValueEquals("creater",creater);
		
		if(StringUtils.isNotBlank(sn))
			taskQuery=taskQuery.processVariableValueEquals("sn", sn);
		
		if(StringUtils.isNotBlank(title))
			taskQuery=taskQuery.processVariableValueEquals("title", title);
		
		if(StringUtils.isNotBlank(flow))
			taskQuery=taskQuery.processDefinitionKey(flow);
		
		Long  count=taskQuery.count();
		List<Task> tasks=taskQuery.orderByTaskCreateTime().desc().listPage(firstResult, pageSize);
		List<ActivitDTO> dtos=new ArrayList<ActivitDTO>();
		for(Task task:tasks){
			ActivitDTO dto=new ActivitDTO();
			dto.setSn((String)processEngine.getTaskService().getVariable(task.getId(), "sn"));
			dto.setTitle((String)processEngine.getTaskService().getVariable(task.getId(), "title"));
			User user=userRepository.findOne((Long)processEngine.getTaskService().getVariable(task.getId(), "creater"));
			dto.setCreater(user);
			dto.setCreateDate(task.getCreateTime());
			dto.setStepName(task.getName());
			dto.setStepId(task.getId());
			dto.setStepFormKey(task.getFormKey());
			dto.setExecutionId(task.getExecutionId());
			dto.setProcessDefinitionId(task.getProcessDefinitionId());
			dto.setBeginDate(task.getCreateTime());
			dto.setProcessInstanceId(task.getProcessInstanceId());
			dtos.add(dto);
		}
		int pageNumber = (int) (firstResult / pageSize) + 1;
		Pageable pageable =new PageRequest(pageNumber, pageSize); 
		Page<ActivitDTO> pageActivitDTOs = new PageImpl<ActivitDTO>(dtos, pageable, count);
		return pageActivitDTOs;
	} 
	
	/***
	 * 获取已办事项
	 * @param begin
	 * @param end
	 * @param creater
	 * @param sn
	 * @param title
	 * @param flow
	 * @return
	 * @throws ParseException
	 */
	public Page<ActivitDTO> findTaskdone( final Date begin, final Date end, final Long creater,
			 final String sn, final String title, final String state,final String flow,Long owner,Integer firstResult,Integer pageSize) throws ParseException{
		HistoricProcessInstanceQuery historicProcessInstanceQuery= processEngine.getHistoryService().createHistoricProcessInstanceQuery().involvedUser(String.valueOf(owner));
		if(begin!=null)	
			historicProcessInstanceQuery=historicProcessInstanceQuery.startedAfter(begin);
		
		if(end!=null)
			historicProcessInstanceQuery=historicProcessInstanceQuery.startedBefore(DateUtils.addDays(end,1));
		
		if(creater!=null)
			historicProcessInstanceQuery=historicProcessInstanceQuery.variableValueEquals("creater",creater);
		
		if(StringUtils.isNotBlank(sn))
			historicProcessInstanceQuery=historicProcessInstanceQuery.variableValueEquals("sn", sn);
		
		if(StringUtils.isNotBlank(title))
			historicProcessInstanceQuery=historicProcessInstanceQuery.variableValueEquals("title", title);
		
		if(StringUtils.isNotBlank(flow))
			historicProcessInstanceQuery=historicProcessInstanceQuery.processDefinitionKey(flow);
		
		if("0".equals(state))
			historicProcessInstanceQuery=historicProcessInstanceQuery.finished();
		
		if("1".equals(state))
			historicProcessInstanceQuery=historicProcessInstanceQuery.unfinished();
		
		Long  count=historicProcessInstanceQuery.count();
		List<HistoricProcessInstance> historicProcessInstances=historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc().listPage(firstResult, pageSize);
		List<ActivitDTO> dtos=new ArrayList<ActivitDTO>();
		/**封装成前台dto*/
		for(HistoricProcessInstance historicProcessInstance:historicProcessInstances){
			ActivitDTO dto=new ActivitDTO();
			
			HistoricVariableInstance  snVar=processEngine.getHistoryService().createHistoricVariableInstanceQuery().
					processInstanceId(historicProcessInstance.getId())
					.variableName("sn").singleResult();
			dto.setSn(snVar==null?"":snVar.getValue().toString());
			
			dto.setTitle((String)processEngine.getHistoryService().createHistoricVariableInstanceQuery().
					processInstanceId(historicProcessInstance.getId())
					.variableName("title").singleResult().getValue());
			
			User user=userRepository.findOne((Long)processEngine.getHistoryService().createHistoricVariableInstanceQuery().
					processInstanceId(historicProcessInstance.getId())
					.variableName("creater").singleResult().getValue());
			dto.setCreater(user);
			
			/**查询流程状态*/
			ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(historicProcessInstance.getId()).singleResult();
			if(pi!=null){
				dto.setState("进行中");
				/**如果没有运行中的usertask，说明任务走到了事件节点，需要显示最近一步的usertask*/
				List<Task> curTasks=processEngine.getTaskService().createTaskQuery().processInstanceId(historicProcessInstance.getId()).active().list();
				if(curTasks.size()==0){
					dto.setStepName("等待事件");
				}else{
					Task curTask= curTasks.get(0);
					dto.setStepName(curTask.getName());
					dto.setStepId(curTask.getId());
				}
				
			}else{
				dto.setState("已结束");
				dto.setStepName("");
				dto.setStepId("");
			}
			dto.setCreateDate(historicProcessInstance.getStartTime());
			
			dto.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
			dto.setBeginDate(historicProcessInstance.getStartTime());
			dto.setProcessInstanceId(historicProcessInstance.getId());
			dtos.add(dto);
		}
		int pageNumber = (int) (firstResult / pageSize) + 1;
		Pageable pageable =new PageRequest(pageNumber, pageSize); 
		Page<ActivitDTO> pageActivitDTOs = new PageImpl<ActivitDTO>(dtos, pageable, count);
		return pageActivitDTOs;
	}
}
