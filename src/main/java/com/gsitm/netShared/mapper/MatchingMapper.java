package com.gsitm.netshared.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.gsitm.netshared.dto.LeaderVO;
import com.gsitm.netshared.dto.MatchingVO;
import com.gsitm.netshared.dto.MemberVO;

@Repository
@Mapper
public interface MatchingMapper {
	// 파티원 신청자가 신청 가능한지 판별.
	public boolean isPossibleToWaitMember(String userId);

	// 파티장 신청자가 신청 가능한지.
	public boolean isPossibleToWaitLeader(String userId);

	// 신청자가 wait테이블에 존재하는지 체크
	public boolean isExistWaiting(String userId, String tableName);

	// 일반 매칭 - 파티원 insert
	public void insertWaitMember(String userId);

	// 일반 매칭 - 파티원 update
	public void updateWaitMember(String userId);

	// 일반 매칭 - 파티장 insert
	public void insertWaitLeader(String userId, String netId, String netPassword, int HNOM);

	// 일반 매칭 - 파티장 update
	public void updateWaitLeader(String userId, String netId, String netPassword, int HNOM);

	// 매칭 가능한 리더 찾기(맴버가 매칭 신청 시)
	public LeaderVO searchLeaderToMatching();

	// 매칭 가능한 맴버 찾기(리더가 매칭 신청 시)
	public ArrayList<MemberVO> searchMemberToMatching();

	// 매칭아이디 조회
	public int getMatchingId(MatchingVO matchingVO);

	// 매칭테이블 데이터 삽입
	public void insertMatchingInfo(MatchingVO matchingVO);

	// 파티 맴버 삽입
	public void insertPartyMember(int matchingId, String leaderId, MemberVO memberVO);

	// 파티 맴버 삽입 (v2)
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
