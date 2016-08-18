package com.pzy.jcook.sys.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.repository.UserRepository;

@Service
public class UserService extends BaseService<User, Long> {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findByUsername(String username){
		return this.userRepository.findByUsername(username);
	}
}
