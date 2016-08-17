package com.pzy.jcook.sys;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.pzy.jcook.sys.entity.BaseEntity;
import com.pzy.jcook.sys.repository.BaseRepository;


public class BaseService <M extends BaseEntity, ID extends Serializable> {
	
	@Autowired
    protected BaseRepository<M, ID> baseRepository;

    /**
     * 保存单个实体
     *
     * @param m 实体
     * @return 返回保存的实体
     */
    public M save(M m) {
        return baseRepository.save(m);
    }
}
