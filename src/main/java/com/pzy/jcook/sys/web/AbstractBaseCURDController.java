package com.pzy.jcook.sys.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pzy.jcook.dto.json.ObjectResponse;
import com.pzy.jcook.dto.json.Response;
import com.pzy.jcook.dto.json.SuccessResponse;
import com.pzy.jcook.sys.entity.BaseEntity;
import com.pzy.jcook.sys.service.BaseService;

public abstract  class AbstractBaseCURDController<M extends BaseEntity<?>, ID extends Serializable> implements BaseCURDController<M , ID >{
	
	@Autowired
	private BaseService<M, ID> baseService;
	
	abstract String getBasePath();
	
	@Override
	@RequestMapping("index")
	public String index(Model model) {
		return this.getBasePath()+"/index";
	}

	@Override
	@RequestMapping("save")
	public Response save(M m) {
		baseService.save(m);
		return new SuccessResponse();
	}

	@Override
	@RequestMapping("update")
	public Response update(M m) {
		baseService.save(m);
		return new SuccessResponse();
	}

	@Override
	@RequestMapping("delete")
	public Response delete(ID id) {
		baseService.delete(id);
		return new SuccessResponse();
	}

	@Override
	@RequestMapping("list")
	public Response list(Integer start, Integer length, String name) {
		int pageNumber = (int) (start / length) + 1;
		int pageSize = length;
		Page<M> m = baseService.findAll(pageNumber, pageSize, name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", m.getContent());
		map.put("iTotalRecords", m.getTotalElements());
		map.put("iTotalDisplayRecords", m.getTotalElements());
		return new ObjectResponse<Map<String,Object>>(map);
	}

}
