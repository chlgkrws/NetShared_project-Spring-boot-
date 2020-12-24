package com.gsitm.netshared.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.gsitm.netshared.dto.LeaderVO;
import com.gsitm.netshared.dto.MatchingVO;
import com.gsitm.netshared.dto.MemberVO;

@Service
public interface MatchingService {
	// 파티원으로 신청가능 한지 체크
	public boolean isPossibleToWaitMember(String userId);

	// 파티장으로 신청가능 한지 체크
	public boolean isPossibleToWaitLeader(String userId);

	// 파티원 insert
	public void insertMember(String userId);

	// 파티장 insert
	public void insertLeader(String userId, String netId, String netPassword, int HNOM);

	// 대기 중인 파티장 조회
	public LeaderVO searchLeaderToMatching();

	// 대기 중인 파티원 조회
	public ArrayList<MemberVO> searchMemberToMatching();

	// 매칭 아이디 조회
	public int getMatchingId(MatchingVO matchingVO);

	// 파티 맴버 삽입
	public void insertPartyMember(int matchingId, String leaderId, MemberVO memberVO);

	// 파티 맴버 삽입(v2)
	public void insertPartyMember(int matchingId, String leaderId, String userId);

	// member_wait안에 is_wait데이터 false로 변환
	public void updateIswaitToFalseInMemberWait(MemberVO memberVO);

	// leader_wait안에 is_wait데이터 false로 변환
	public void updateIswaitToFalseInLeaderWait(String leaderId);

	// 매칭 중인 파티 중 빈자리가 있는지 확인
	public MatchingVO canIJoinYourMatching();

	// 현재 매칭테이블에 매칭된 파티원 수
	public int getPresentMatchingMember(int matchingId);

	// 파티원 수가 다 찼을 때
	public void setIsFullTrue(int matchingId);

	// member_wait안에 is_wait데이터 false로 삽입
	public void insertIswaitToFalseInMemberWait(String userId);
}
