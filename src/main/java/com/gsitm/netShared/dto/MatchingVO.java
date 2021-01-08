package com.gsitm.netshared.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MatchingVO {
	private int matchingId;
	private String leaderId;
	private int	dcPercent;
	private int maxNumberOfMember;
	private boolean isFull;
	private boolean isNormal;
	private int howLongUse;
	private String netId;
	private String netPassword;
	private Timestamp waitingTime;
	private Timestamp createdTime;
	private Timestamp updateTime;
	private boolean wouldUYN;


}
