package com.gsitm.netShared.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MatchingVO {
	private int matching_id;
	private String leader_id;
	private int	dcPercent;
	private int maxNumberOfMember;
	private boolean isFull;
	private boolean isNormal;
	private int howLongUse;
	private String net_id;
	private String net_password;
	private Timestamp waitingTime;
	private Timestamp createdTime;
	private Timestamp updateTime;
	private boolean wouldUYN;


}
