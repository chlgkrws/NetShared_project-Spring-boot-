package com.it1457.vo;

/*
CREATE TABLE `board_tbl` (
		  `board_id` int(20) NOT NULL AUTO_INCREMENT,
		  `user_id` varchar(50) COLLATE utf8_bin NOT NULL,
		  `writer` varchar(20) COLLATE utf8_bin NOT NULL,
		  `title` varchar(50) COLLATE utf8_bin NOT NULL,
		  `content` longtext COLLATE utf8_bin NOT NULL,
		  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
		  `recommend` int(11) NOT NULL DEFAULT '0',
		  `genre` varchar(30) COLLATE utf8_bin NOT NULL,
		  `valid` tinyint(4) NOT NULL DEFAULT '1',
		  `star_rate` int(4) NOT NULL DEFAULT '0',
		  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
		  PRIMARY KEY (`board_id`),
		  KEY `foreign_user_id_idx` (`user_id`),
		  CONSTRAINT `foreign_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
		) ENGINE=InnoDB AUTO_INCREMENT=32795 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

*/
public class UserVO {
	private String id;
	private String password;
	private String userName;
	private String identity;
	private String phone;
	private String account;
	private String auth;
	private String createdTime;
	private String updateTime;
	private boolean wouldUYN;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public boolean isWouldUYN() {
		return wouldUYN;
	}
	public void setWouldUYN(boolean wouldUYN) {
		this.wouldUYN = wouldUYN;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	
	
}
