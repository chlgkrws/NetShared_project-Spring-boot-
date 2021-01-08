package com.gsitm.netshared.vo;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	private String empNo;
	private String userName;
	private Date birth;
	private String originDept;
	private String currentDept;
	private Date hireDate;
	private String expTime;
	private String currentJob;
	private String currentProject;
	private String jobGoal1;
	private String jobGoal2;
	private String jobGoal3;
	private String domainGoal1;
	private String domainGoal2;
	private String domainGoal3;
	private String eduGoal1;
	private String eduGoal2;
	private String eduGoal3;
	private String careerGoal1;
	private String careerGoal2;
	private String careerGoal3;



}
