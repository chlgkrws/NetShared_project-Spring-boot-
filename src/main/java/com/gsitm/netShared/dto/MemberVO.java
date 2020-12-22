package com.it1457.vo;

import java.sql.Timestamp;

public class MemberVO {
	private String userId;
	private boolean isWait;
	private Timestamp waitingTime;
	private Timestamp createdTime;
	private Timestamp updateTime;
	private boolean wouldUYN;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isWait() {
		return isWait;
	}
	public void setWait(boolean isWait) {
		this.isWait = isWait;
	}
	public Timestamp getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(Timestamp waitingTime) {
		this.waitingTime = waitingTime;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public boolean isWouldUYN() {
		return wouldUYN;
	}
	public void setWouldUYN(boolean wouldUYN) {
		this.wouldUYN = wouldUYN;
	}
	
	
}
