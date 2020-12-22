package com.gsitm.netShared.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class LeaderVO {
	private String userId;
	private boolean isWait;
	private boolean isNormal;
	private int dcPercent ;
	private int maxNumberOfMember;
	private int howLongUse;
	private String netId;
	private String netPassword;
	private Timestamp waitingTime;
	private Timestamp createdTime;
	private Timestamp updateTime;
	private boolean wouldUYN;

}
