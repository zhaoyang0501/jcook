package com.pzy.jcook.sys.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_users")
public class User extends BaseEntity<Long>{
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String salt;
	
	
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
