package com.pzy.jcook.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.pzy.jcook.sys.entity.BaseEntity;

@Entity
@Table(name = "t_workitem")
public class Workitem  extends BaseEntity<Long>{

}
