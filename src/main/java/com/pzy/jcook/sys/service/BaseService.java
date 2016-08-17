package com.pzy.jcook.sys.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.pzy.jcook.sys.entity.BaseEntity;
import com.pzy.jcook.sys.repository.BaseRepository;


public class BaseService <M extends BaseEntity, ID extends Serializable> {
	@Autowired
    protected BaseRepository<M, ID> baseRepository; 
	
	public M save(M m) {
	    return baseRepository.save(m);
	}
	
	public void delete(M m){
		baseRepository.delete(m);
	}
}
