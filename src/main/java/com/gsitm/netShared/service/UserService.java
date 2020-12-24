package com.gsitm.netshared.service;

import org.springframework.stereotype.Service;

import com.gsitm.netshared.dto.UserVO;

@Service
public interface UserService {

	// 회원가입
	public void userInsert(UserVO userVO);

	// 로그인
	public UserVO userSearch(String id);

	// 개인정보 수정
	public int updateInfo(UserVO userVO);

	// 회원탈퇴
	public int withdrawUser(String userId);

}
