package com.gsitm.netshared.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;

import com.gsitm.netshared.dto.UserVO;

@Repository
@Mapper
public interface UserMapper {
	// 회원가입
	public void userInsert(UserVO userVO);

	// 로그인
	public UserVO userSearch(String id);

	// 개인정보 수정
	public int updateUserInfo(UserVO userVO);

	// 회원탈퇴
	public int withdrawUser(String userId);

}
