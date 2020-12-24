package com.gsitm.netshared.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		System.out.println("(DAO - isPossibleToWaitMember)isExist : " + isExist);

		// 이미 대기열에 존재한다면
		if (isExist) {
			// 대기열에 존재하지만 이미 매칭된 사람의 매칭 취소인지, 사용중인지 판별
			CheckAlreadyUsedPstmt = conn
					.prepareStatement("select wouldUYN from party_member_info_tbl where member_id = ?");
			CheckAlreadyUsedPstmt.setString(1, userId);
			rs = CheckAlreadyUsedPstmt.executeQuery();

			HashSet<Integer> checkTF = new HashSet<>();
			boolean isUsed = rs.next();
			System.out.println("(DAO - isPossibleToWaitMember)isUsed : " + isUsed);
			// 여기12-03했습니다.
			// 반환된 값이 있으면
			if (isUsed) {
				checkTF.add(rs.getInt(1));
				while (rs.next()) {
					checkTF.add(rs.getInt(1));
				}
				// 과거 기록 + 현재 진행 매칭 = 2 = 현재 사용 중
				if (checkTF.size() == 2) {
					System.out.println("(DAO - isPossibleToWaitMember)이미 대기열 존재, 이미 매칭된 유저");
					return false;
					// 결과값 1 = 현재 매칭된 상태 or 파티탈퇴(이후 재 매칭 안한 경우)
				} else {
					// v == 0은 파티 탈퇴한 상황
					for (Integer v : checkTF) {
						if (v == 0) {
							System.out
									.println("(DAO - isPossibleToWaitMember)이미 대기열에 존재하지만 이전 파티를 탈퇴하고 다시 매칭을 신청하는 유저");
							// update 쿼리 필요
							return true;
						}
					}
				}
				System.out.println(
						"(DAO - isPossibleToWaitMember)이미 대기열에 존재하고, 이전 매칭 기록도 있음. 논리적 오류(wouldUYN : 1, size : 1인경우)");
				return false;
			} else {
				// 예외 케이스
				// 대기열에 존재하는 사람은 이전에 서비스를 이용했거나, 지금 대기중인가 인데,
				// 현재 조건문에 들어온 이유는 이미 대기열에 존재한다는 것이지만
				// 매칭을 한적이 없다는 것이다. 비정상 부분
				System.out.println("(DAO - isPossibleToWaitMember)예외 케이스 비정상 종료");
				return false;
			}
			// 대기열에 존재하지 않으면 신청 가능 true반환
		} else {
			// 삽입 쿼리 넣기
			System.out.println("(DAO - isPossibleToWaitMember)대기열에 존재하지 않는 유저 ");
			return true;
		}

		// 현재 메서드는 신청해도 되는지 안되는지 판별한다.
		// 신청할 수 있으면 true 반환, 신청할 수 없으면 false 반환
		// 신청할 수 있는 사람은 db에 (wait에 데이터가 아예 없거나, [**있어도, is_wait가 false여야한다.) 만약 is_wait가
		// true면 이미 신청한 것이기 때문에 신청할 수 없다.]
		// **is_wait가 false인 사람이라면, 현재 서비스를 사용하는지 확인해야한다.
		// 그럼 하나의 로우를 가져와서 없는 결과라면 true를 반환해주고, 있다면 is_wait의 값을 확인한다.
		// is wait가 true(대기상태)이면 false(신청불가/중복신청)를 반환

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

	// 신청자가 wait테이블에 존재하는지 체크
	public boolean isExistWaiting(String userId, String tableName) {
		try {
			pstmt = conn.prepareStatement("select user_id, is_wait from " + tableName + " where user_id = ?");
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			// 값이 있으면 기다릴 수 없음.(= 업데이트를 해야함)
			if (rs.next()) {
				return true;
			}

			return false;
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - isExistWaiting) 에러");
		} finally {
			close(conn, pstmt);
		}
		return false;
	}

}
