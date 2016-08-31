package jcook.test;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pzy.jcook.SpringBootContext;
import com.pzy.jcook.workflow.service.WorkFlowService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringBootContext.class)
public class WorkFlowTest {
	
	@Autowired
	private WorkFlowService workFlowService;
	@Test
	public void taskTodo() throws ParseException  {
		System.out.println("ddd");
		workFlowService.findTaskTodo(null, null, null, null, null, null, null, 1, 23);
	}
}
