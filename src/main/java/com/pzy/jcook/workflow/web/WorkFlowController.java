package com.pzy.jcook.workflow.web;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.jcook.dto.json.DataTableResponse;
import com.pzy.jcook.dto.json.Response;
import com.pzy.jcook.workflow.dto.ActivitDTO;
import com.pzy.jcook.workflow.service.WorkFlowService;

@Controller
@RequestMapping("workflow")
public class WorkFlowController {

	@Autowired
	WorkFlowService workFlowService;

	@RequestMapping("tasktodo")
	public String index(Model model) {
		return  "workflow/tasktodo";
	}

	
	@RequestMapping("tasktodolist")
	@ResponseBody
	public Response list(Integer start, Integer length, String name) throws ParseException {
		Page<ActivitDTO> m = workFlowService.findTaskTodo(null, null, null, null, null, null, 1l, start, length);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", m.getContent());
		map.put("recordsTotal", m.getNumber());
		map.put("recordsFiltered",m.getNumber());
		map.put("draw", 1);
		return new DataTableResponse<ActivitDTO>( m.getContent(),(int) m.getTotalElements() );
	}
}
