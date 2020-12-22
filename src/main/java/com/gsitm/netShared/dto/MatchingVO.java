package com.it1457.vo;

import java.sql.Timestamp;

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
	
	
	public int getMatching_id() {
		return matching_id;
	}
	public void setMatching_id(int matching_id) {
		this.matching_id = matching_id;
	}
	public String getLeader_id() {
		return leader_id;
	}
	public void setLeader_id(String leader_id) {
		this.leader_id = leader_id;
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
	public boolean isFull() {
		return isFull;
	}
	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
	public boolean isNormal() {
		return isNormal;
	}
	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}
	public int getHowLongUse() {
		return howLongUse;
	}
	public void setHowLongUse(int howLongUse) {
		this.howLongUse = howLongUse;
	}
	public String getNet_id() {
		return net_id;
	}
	public void setNet_id(String net_id) {
		this.net_id = net_id;
	}
	public String getNet_password() {
		return net_password;
	}
	public void setNet_password(String net_password) {
		this.net_password = net_password;
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
	public Timestamp getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(Timestamp waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	
	
}
