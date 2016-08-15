package com.pzy.jcook.sys.repository;

import org.springframework.data.repository.CrudRepository;

import com.pzy.jcook.sys.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
