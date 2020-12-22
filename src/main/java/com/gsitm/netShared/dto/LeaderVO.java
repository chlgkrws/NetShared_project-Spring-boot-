package com.it1457.vo;

import java.sql.Timestamp;

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
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isNormal() {
		return isNormal;
	}
	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}
	public int getDcPercent() {
		return dcPercent;
	}
	public void setDcPercent(int dcPercent) {
		this.dcPercent = dcPercent;
	}
	public int getMaxNumberOfMember() {
		return maxNumberOfMember;
	}
	public void setMaxNumberOfMember(int maxNumberOfMember) {
		this.maxNumberOfMember = maxNumberOfMember;
	}
	public int getHowLongUse() {
		return howLongUse;
	}
	public void setHowLongUse(int howLongUse) {
		this.howLongUse = howLongUse;
	}
	public String getNetId() {
		return netId;
	}
	public void setNetId(String netId) {
		this.netId = netId;
	}
	public String getNetPassword() {
		return netPassword;
	}
	public void setNetPassword(String netPassword) {
		this.netPassword = netPassword;
	}
	public Timestamp getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(Timestamp waitingTime) {
		this.waitingTime = waitingTime;
	}
	public boolean isWait() {
		return isWait;
	}
	public void setWait(boolean isWait) {
		this.isWait = isWait;
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
