package jcook.test;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.pzy.jcook.SpringBootContext;
import com.pzy.jcook.sys.service.BaseService;
import com.pzy.jcook.sys.service.UserService;
import com.pzy.jcook.workflow.dto.ActivitDTO;
import com.pzy.jcook.workflow.dto.ShortUrlDTO;
import com.pzy.jcook.workflow.entity.Workitem;
import com.pzy.jcook.workflow.service.WorkFlowService;
import com.pzy.jcook.workflow.service.WorkitemService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringBootContext.class)
public class WorkFlowTest {
	
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private WorkitemService workitemService;
	@Autowired
	BaseService<Workitem,Long> baseService;
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	UserService userService ;
	
	@Test
	public void getrole() throws ParseException  {
		//userService.singlefindByRole(1l);
	}
	
	@Test
	public void taskTodo() throws ParseException  {
		System.out.println("ddd");
		Page<ActivitDTO> list= workFlowService.findTaskTodo(null, null, null, null, null, null, 1l, 0, 23);
		System.out.println(list.getContent().size());
	}
	
	@Test
	public void deploy(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("processes/workitem.zip");  
	    ZipInputStream zipInputStream = new ZipInputStream(in);  
	    Deployment deployment = processEngine.getRepositoryService()
	                    .createDeployment()
	                    .addZipInputStream(zipInputStream)
	                    .deploy();
	    Assert.notNull(deployment);
		  
	}
	@Test
	public void start(){
		Workitem workitem = new Workitem();
		baseService.save(workitem);
		/**发起流程*/
		Map<String,Object> activtiMap=new HashMap<String,Object>();
		activtiMap.put("title","XX 工作任务4");
		activtiMap.put("creater",1l);
		ProcessInstance processInstance=processEngine.getRuntimeService().startProcessInstanceByKey
				("workitem", workitem.getId().toString(),activtiMap);
		/**完成第一步（提交申请）*/	
		String sn="WORKITEM"+DateFormatUtils.format(new Date(),"_yyyy_MM_")+processInstance.getId();
		processEngine.getRuntimeService().setVariable(processInstance.getProcessInstanceId(), "sn", sn);
		
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().
				processInstanceId(processInstance.getId()).list();
		processEngine.getTaskService().complete(tasks.get(0).getId(),activtiMap);
		  
	}
	@Test
	public void shortUrl(){
		Map<String ,Object> urlVariables = new HashMap<String ,Object>(); 
		urlVariables.put("url", "www.baidu.com"); 
		
		String dto = this.restTemplate.getForObject("http://www.cnbeta.com", null, String.class,urlVariables);
		System.out.println(dto);
	}
}
