package com.pzy.jcook.workflow.dto;

import java.util.Date;

import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.workflow.entity.Workitem;

public class WorkItemDTO {
	
	
	public static final String STATE_RUNING="进行中";
	public static final String STATE_END="已结束";
	
	private Long id;
	private String title;
	private User leader;
	private Date createDate;
	private Date endDate;
	private String step;
	private String state;
	
	public WorkItemDTO(Workitem workItem){
		this.id=workItem.getId();
		this.title = workItem.getTitle();
		this.leader = workItem.getLeader();
		this.createDate = workItem.getCreateDate();
		this.state= STATE_RUNING;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getLeader() {
		return leader;
	}
	public void setLeader(User leader) {
		this.leader = leader;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
