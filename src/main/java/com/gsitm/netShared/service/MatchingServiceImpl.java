package com.gsitm.netshared.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsitm.netshared.dto.LeaderInfoVO;
import com.gsitm.netshared.dto.LeaderVO;
import com.gsitm.netshared.dto.MatchingVO;
import com.gsitm.netshared.dto.MemberVO;
import com.gsitm.netshared.mapper.MatchingMapper;

@Service
public class MatchingServiceImpl implements MatchingService {

	@Autowired
	public MatchingMapper matchingMapper;

	// 파티원으로 신청가능 한지 체크
	public boolean isPossibleToWaitMember(String userId) {
		String tableName = "member_wait_tbl";
		boolean isExist = isExistWaiting(userId, tableName); // true면 값이 있음, false면 값이 없음 || 값이 있으면 대기, 없으면 가능

		// 이미 대기열에 존재한다면
		if (isExist) {
			// 대기열에 존재하지만 이미 매칭된 사람의 매칭 취소인지, 사용중인지 판별
			ArrayList<Integer> wouldUYNCount = matchingMapper.isPossibleToWaitMember(userId);

			// 과거 기록 + 현재 진행 매칭 = 2 = 현재 사용 중
			if(wouldUYNCount.size() == 2) {
				return false;
			// 결과값 1 = 현재 매칭된 상태 or 파티탈퇴(이후 재 매칭 안한 경우)
			}else if(wouldUYNCount.size() == 1) {
				if(wouldUYNCount.get(0) == 0) {
					return false;
				}else {
					return true;
				}
			//매칭을 한 적 없는 유저
			}else {
				return true;
			}

		// 현재 메서드는 신청해도 되는지 안되는지 판별한다.
		// 신청할 수 있으면 true 반환, 신청할 수 없으면 false 반환
		// 신청할 수 있는 사람은 db에 (wait에 데이터가 아예 없거나, [**있어도, is_wait가 false여야한다.) 만약 is_wait가
		// true면 이미 신청한 것이기 때문에 신청할 수 없다.]
		// **is_wait가 false인 사람이라면, 현재 서비스를 사용하는지 확인해야한다.
		// 그럼 하나의 로우를 가져와서 없는 결과라면 true를 반환해주고, 있다면 is_wait의 값을 확인한다.
		// is wait가 true(대기상태)이면 false(신청불가/중복신청)를 반환
		}else {
			return true;
		}
	}

	// 파티장으로 신청가능 한지 체크
	public boolean isPossibleToWaitLeader(String userId) {
		return matchingMapper.isPossibleToWaitLeader(userId) > 0 ? false : true;
	}

	// 파티원 insert
	public void insertMember(String userId) {
		boolean isExist = true;
		try {
			isExist = matchingMapper.isExistWaiting(userId, "member_wait_tbl") > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!isExist) {
			matchingMapper.insertWaitMember(userId);
		} else {
			matchingMapper.updateWaitMember(userId,  new Timestamp(System.currentTimeMillis()));
		}
	}

	// 파티장 insert
	public void insertLeader(String userId, String netId, String netPassword, int HNOM) {
		boolean isExist = true;
		try {
			isExist = matchingMapper.isExistWaiting(userId, "leader_wait_tbl") > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!isExist) {
			matchingMapper.insertWaitLeader(userId, netId, netPassword, HNOM);
		} else {
			matchingMapper.updateWaitLeader(userId, netId, netPassword, HNOM,  new Timestamp(System.currentTimeMillis()));
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

	// 신청자가 wait테이블에 존재하는지 체크
	public boolean isExistWaiting(String userId, String tableName) {
		try {
			return matchingMapper.isExistWaiting(userId, tableName) > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	//매칭 테이블 생성
	@Override
	public void insertMatchingInfo(MatchingVO matchingVO) {
		matchingMapper.insertMatchingInfo(matchingVO);
	}

	// 멤버로 신청한 파티가 있는지 확인
	public LeaderInfoVO getLeaderInfo(String userId) {
		return matchingMapper.getLeaderInfo(userId);
	}

	//매칭취소
	public void matchingCancel(String userId) {
		matchingMapper.matchingCancel(userId);
	}

	@Override
	public void insertWaitLeaderQuick(String userId, boolean isNormal, int HNOM, String netId, String netPassword, int leastVal,
			int dcCount) {
		matchingMapper.insertWaitLeaderQuick(userId, isNormal, HNOM, netId, netPassword, leastVal, dcCount);
	}

	@Override
	public void updateWaitLeaderQuick(String userId) {

	}

	@Override
	public ArrayList<LeaderVO> getLeaderVOList() {
		return matchingMapper.getLeaderVOList();
	}

}
