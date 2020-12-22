package com.gsitm.netShared.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsitm.netShared.dto.LeaderVO;
import com.gsitm.netShared.dto.MatchingVO;
import com.gsitm.netShared.dto.MemberVO;
import com.gsitm.netShared.mapper.MatchingMapper;


@Service
public class MatchingServiceImpl {

	@Autowired
	public MatchingMapper matchingMapper;


	// 파티원으로 신청가능 한지 체크
	public boolean isPossibleToWaitMember(String userId) {
		return matchingMapper.isPossibleToWaitMember(userId);
	}

	// 파티장으로 신청가능 한지 체크
	public boolean isPossibleToWaitLeader(String userId) {
		return matchingMapper.isPossibleToWaitLeader(userId);
	}

	// 파티원 insert
	public void insertMember(String userId) {
		boolean isExist = matchingMapper.isExistWaiting(userId, "member_wait_tbl");

		if (!isExist) {
			matchingMapper.insertWaitMember(userId);
		} else {
			matchingMapper.updateWaitMember(userId);
		}
	}

	// 파티장 insert
	public void insertLeader(String userId, String netId, String netPassword, int HNOM) {
		boolean isExist = matchingMapper.isExistWaiting(userId, "leader_wait_tbl");

		if (!isExist) {
			matchingMapper.insertWaitLeader(userId, netId, netPassword, HNOM);
		} else {
			matchingMapper.updateWaitLeader(userId, netId, netPassword, HNOM);
		}
	}

	// 대기 중인 파티장 조회
	public LeaderVO searchLeaderToMatching() {
		return matchingMapper.searchLeaderToMatching();
	}

	// 대기 중인 파티원 조회
	public ArrayList<MemberVO> searchMemberToMatching() {
		return matchingMapper.searchMemberToMatching();
	}

	// 매칭 아이디 조회
	public int getMatchingId(MatchingVO matchingVO) {
		return matchingMapper.getMatchingId(matchingVO);
	}

	// 파티 맴버 삽입
	public void insertPartyMember(int matchingId, String leaderId, MemberVO memberVO) {
		matchingMapper.insertPartyMember(matchingId, leaderId, memberVO);
	}

	// 파티 맴버 삽입(v2)
	public void insertPartyMember(int matchingId, String leaderId, String userId) {
		matchingMapper.insertPartyMember(matchingId, leaderId, userId);
	}

	// member_wait안에 is_wait데이터 false로 변환
	public void updateIswaitToFalseInMemberWait(MemberVO memberVO) {
		matchingMapper.updateIswaitToFalseInMemberWait(memberVO);
	}

	// leader_wait안에 is_wait데이터 false로 변환
	public void updateIswaitToFalseInLeaderWait(String leaderId) {
		matchingMapper.updateIswaitToFalseInLeaderWait(leaderId);
	}

	// 매칭 중인 파티 중 빈자리가 있는지 확인
	public MatchingVO canIJoinYourMatching() {
		return matchingMapper.canIJoinYourMatching();
	}

	// 현재 매칭테이블에 매칭된 파티원 수
	public int getPresentMatchingMember(int matchingId) {
		return matchingMapper.getPresentMatchingMember(matchingId);
	}

	// 파티원 수가 다 찼을 때
	public void setIsFullTrue(int matchingId) {
		matchingMapper.setIsFullTrue(matchingId);
	}

	// member_wait안에 is_wait데이터 false로 삽입
	public void insertIswaitToFalseInMemberWait(String userId) {
		matchingMapper.insertIswaitToFalseInMemberWait(userId);
	}

}
