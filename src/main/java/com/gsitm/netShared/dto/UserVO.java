package com.gsitm.netshared.dto;

import lombok.Data;

@Data
public class UserVO {
	private String userId;
	private String password;
	private String userName;
	private String identity;
	private String phone;
	private String account;
	private String auth;
	private String createdTime;
	private String updateTime;
	private boolean wouldUYN;

}
