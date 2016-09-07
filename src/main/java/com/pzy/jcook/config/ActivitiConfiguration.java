package com.pzy.jcook.config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import com.pzy.jcook.workflow.listener.TaskAssignedListener;

/***
 * 工作流相关配置  监听等
 * @author panchaoyang
 *
 */
@Configuration
public class ActivitiConfiguration extends AbstractProcessEngineAutoConfiguration {
	@Bean
    @ConditionalOnMissingBean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            PlatformTransactionManager transactionManager,
            DataSource dataSource,
            SpringAsyncExecutor springAsyncExecutor) throws IOException {

    	SpringProcessEngineConfiguration springProcessEngineConfiguration =  baseSpringProcessEngineConfiguration(
        		dataSource,
                transactionManager,
                springAsyncExecutor);
    	
        Map<String,List<ActivitiEventListener>> map = new HashMap<String,List<ActivitiEventListener>> ();
		List<ActivitiEventListener> ActivitiEventListenerlists = new ArrayList<ActivitiEventListener>();
		ActivitiEventListenerlists.add(taskAssignedListener());
		
		map.put("TASK_ASSIGNED", ActivitiEventListenerlists);
		springProcessEngineConfiguration.setTypedEventListeners(map);
		return springProcessEngineConfiguration;
    }
    
    @Bean 
	public TaskAssignedListener taskAssignedListener(){
		return new TaskAssignedListener();
	}
}