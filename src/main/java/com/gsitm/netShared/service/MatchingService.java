package com.it1457.service;

import java.util.ArrayList;

import com.it1457.dao.MatchingDAO;
import com.it1457.vo.LeaderVO;
import com.it1457.vo.MatchingVO;
import com.it1457.vo.MemberVO;

public class MatchingService {
	private static MatchingService service = new MatchingService();
	public MatchingDAO dao = MatchingDAO.getInstance();

	private MatchingService() {
	}

	public static MatchingService getInstance() {
		return service;
	}

	// 파티원으로 신청가능 한지 체크
	public boolean isPossibleToWaitMember(String userId) {
		return dao.isPossibleToWaitMember(userId);
	}

	// 파티장으로 신청가능 한지 체크
	public boolean isPossibleToWaitLeader(String userId) {
		return dao.isPossibleToWaitLeader(userId);
	}

	// 파티원 insert
	public void insertMember(String userId) {
		boolean isExist = dao.isExistWaiting(userId, "member_wait_tbl");

		if (!isExist) {
			dao.insertWaitMember(userId);
		} else {
			dao.updateWaitMember(userId);
		}
	}

	// 파티장 insert
	public void insertLeader(String userId, String netId, String netPassword, int HNOM) {
		boolean isExist = dao.isExistWaiting(userId, "leader_wait_tbl");

		if (!isExist) {
			dao.insertWaitLeader(userId, netId, netPassword, HNOM);
		} else {
			dao.updateWaitLeader(userId, netId, netPassword, HNOM);
		}
	}

	// 대기 중인 파티장 조회
	public LeaderVO searchLeaderToMatching() {
		return dao.searchLeaderToMatching();
	}

	// 대기 중인 파티원 조회
	public ArrayList<MemberVO> searchMemberToMatching() {
		return dao.searchMemberToMatching();
	}

	// 매칭 아이디 조회
	public int getMatchingId(MatchingVO matchingVO) {
		return dao.getMatchingId(matchingVO);
	}

	// 파티 맴버 삽입
	public void insertPartyMember(int matchingId, String leaderId, MemberVO memberVO) {
		dao.insertPartyMember(matchingId, leaderId, memberVO);
	}

	// 파티 맴버 삽입(v2)
	public void insertPartyMember(int matchingId, String leaderId, String userId) {
		dao.insertPartyMember(matchingId, leaderId, userId);
	}

	// member_wait안에 is_wait데이터 false로 변환
	public void updateIswaitToFalseInMemberWait(MemberVO memberVO) {
		dao.updateIswaitToFalseInMemberWait(memberVO);
	}
	
	// leader_wait안에 is_wait데이터 false로 변환
	public void updateIswaitToFalseInLeaderWait(String leaderId) {
		dao.updateIswaitToFalseInLeaderWait(leaderId);
	}

	// 매칭 중인 파티 중 빈자리가 있는지 확인
	public MatchingVO canIJoinYourMatching() {
		return dao.canIJoinYourMatching();
	}

	// 현재 매칭테이블에 매칭된 파티원 수
	public int getPresentMatchingMember(int matchingId) {
		return dao.getPresentMatchingMember(matchingId);
	}

	// 파티원 수가 다 찼을 때
	public void setIsFullTrue(int matchingId) {
		dao.setIsFullTrue(matchingId);
	}

	// member_wait안에 is_wait데이터 false로 삽입
	public void insertIswaitToFalseInMemberWait(String userId) {
		dao.insertIswaitToFalseInMemberWait(userId);
	}

}
