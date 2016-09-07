package com.pzy.jcook.workflow.service;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.repository.UserRepository;
import com.pzy.jcook.sys.service.BaseService;
import com.pzy.jcook.workflow.entity.Workitem;

@Service
public class WorkitemService extends BaseService<Workitem, Long> {
	
	@Autowired
	private UserRepository userRepository;
	
	/***
	 * 连同推荐组员一起保存
	 * @param workitem
	 * @param applyuserids
	 */
	public void save(Workitem workitem,String applyuserids){
		if(StringUtils.isNotBlank(applyuserids)){
			Set<User> users = new HashSet<User>();
			String[] userids = applyuserids.split(",");
			for(String id: userids){
				users.add(userRepository.findOne(Long.valueOf(id)));
			}
			workitem.setApplyusers(users);
		}
		this.baseRepository.save(workitem);
	}
	
	
	/***
	 * 连同处理人员一起保存
	 * @param workitem
	 * @param applyuserids
	 */
	public void saveHanderUser(Workitem workitem,String applyuserids){

		if(StringUtils.isNotBlank(applyuserids)){
			Set<User> users = new HashSet<User>();
			String[] userids = applyuserids.split(",");
			for(String id: userids){
				users.add(userRepository.findOne(Long.valueOf(id)));
			}
			workitem.setHandleusers(users);
		}
		this.baseRepository.save(workitem);
	}
	
}
