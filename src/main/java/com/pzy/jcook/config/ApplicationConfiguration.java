package com.pzy.jcook.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;

import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pzy.jcook.workflow.listener.TaskAssignedListener;

@Configuration
public class ApplicationConfiguration {
	
	/*@Bean
	public SpringProcessEngineConfiguration springProcessEngineConfiguration(){
		SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
		
		Map<String,List<ActivitiEventListener>> map = new HashMap<String,List<ActivitiEventListener>> ();
		List<ActivitiEventListener> ActivitiEventListenerlists = new ArrayList<ActivitiEventListener>();
		ActivitiEventListenerlists.add(taskAssignedListener());
		map.put("TASK_ASSIGNED", ActivitiEventListenerlists);
		springProcessEngineConfiguration.setTypedEventListeners(map);
		return springProcessEngineConfiguration;
	}
	*/
	@Bean 
	public TaskAssignedListener taskAssignedListener(){
		return new TaskAssignedListener();
	}
	
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("50MB");
        factory.setMaxRequestSize("50MB");
        return factory.createMultipartConfig();
    }
}
