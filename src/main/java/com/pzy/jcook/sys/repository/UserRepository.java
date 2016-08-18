package com.pzy.jcook.sys.repository;

import com.pzy.jcook.sys.entity.User;

public interface UserRepository   extends BaseRepository<User ,Long>{
	
	public User findByUsername(String username);
}
