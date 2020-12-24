package com.gsitm.netshared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsitm.netshared.dto.UserVO;
import com.gsitm.netshared.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	public UserMapper userMapper;

	//회원가입
	public void userInsert(UserVO userVO) {
		userMapper.userInsert(userVO);
	}

	//로그인
	public UserVO userSearch(String id) {
		UserVO user = null;
		try {
			user = userMapper.userSearch(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
