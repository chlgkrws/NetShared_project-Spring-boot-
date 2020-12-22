package com.gsitm.netShared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsitm.netShared.dto.UserVO;
import com.gsitm.netShared.mapper.UserMapper;

@Service
public class UserServiceImpl {


	@Autowired
	public UserMapper userMapper;

	//회원가입
	public void userInsert(UserVO userVO) {
		userMapper.userInsert(userVO);
	}

	//로그인
	public UserVO userSearch(String id) {
		UserVO user = userMapper.userSearch(id);
		return user;
	}

	//개인정보 수정
	public int updateInfo(UserVO userVO) {
		return userMapper.updateUserInfo(userVO);
	}

	//회원탈퇴
	public int withdrawUser(String userId) {
		return userMapper.withdrawUser(userId);
	}

}
