package com.gsitm.netshared.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberVO {
	private String userId;
	private boolean isWait;
	private Timestamp waitingTime;
	private Timestamp createdTime;
	private Timestamp updateTime;
	private boolean wouldUYN;

}
