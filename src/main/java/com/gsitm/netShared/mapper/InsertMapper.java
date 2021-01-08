package com.gsitm.netshared.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gsitm.netshared.vo.User;

@Repository
@Mapper
public interface InsertMapper {

	//유저 정보 입력
	public void insertUser(@Param("userVO") User userVO );
}
