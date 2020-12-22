package com.it1457.service;

import com.it1457.dao.UserDAO;
import com.it1457.vo.UserVO;

public class UserService {
	private static UserService service = new UserService();
	public UserDAO dao = UserDAO.getInstance();
	
	private UserService(){}

	public static UserService getInstance() {
		return service;
	}
	
	//회원가입 
	public void userInsert(UserVO userVO) {
		dao.userInsert(userVO);
	}
	
	//로그인
	public UserVO userSearch(String id) {
		UserVO user = dao.userSearch(id);
		return user;
	}
	
	//개인정보 수정
	public int updateInfo(UserVO userVO) {
		return dao.updateUserInfo(userVO);
	}
	
	//회원탈퇴
	public int withdrawUser(String userId) {
		return dao.withdrawUser(userId);
	}
	
}
