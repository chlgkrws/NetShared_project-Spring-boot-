package com.gsitm.netShared.mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

import com.gsitm.netShared.dto.LeaderVO;
import com.gsitm.netShared.dto.MatchingVO;
import com.gsitm.netShared.dto.MemberVO;

public class MatchingMapper {

	private static MatchingMapper dao = new MatchingMapper();

	private MatchingMapper() {
	}

	public static MatchingMapper getInstance() {
		return dao;
	}

	public Connection connect() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mariadb://gsitm-intern2020.c5tdqadv8vmd.ap-northeast-2.rds.amazonaws.com/it1457", "it1457",
					"it1457");
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		}
		return conn;
	}

	public void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception ex) {
				System.out.println("오류 발생 : " + ex);
			}
		}
		close(conn, ps);
	} // close

	public void close(Connection conn, PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception ex) {
				System.out.println("오류 발생 : " + ex);
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (Exception ex) {
				System.out.println("오류 발생 : " + ex);
			}
		}
	} // close

	// 파티원 신청자가 신청 가능한지 판별.
	public boolean isPossibleToWaitMember(String userId) {
		Connection conn = null;
		PreparedStatement CheckAlreadyUsedPstmt = null;
		ResultSet rs = null;
		String tableName = "member_wait_tbl";
		try {
			conn = connect();
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
								System.out.println(
										"(DAO - isPossibleToWaitMember)이미 대기열에 존재하지만 이전 파티를 탈퇴하고 다시 매칭을 신청하는 유저");
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

		} catch (Exception ex) {
			System.out.println("(DAO - isPossibleToWaitMember)오류 발생 : " + ex);
			return false;
		} finally {
			close(conn, CheckAlreadyUsedPstmt);
		}
	}

	// 파티장 신청자가 신청 가능한지.
	public boolean isPossibleToWaitLeader(String userId) {
		Connection conn = null;
		PreparedStatement CheckAlreadyUsedPstmt = null;
		String tableName = "leader_wait_tbl";

		ResultSet rs = null;
		try {
			conn = connect();
			boolean isExist = isExistWaiting(userId, tableName);

			System.out.println("(DAO - isPossibleToWaitLeader) isExist : " + isExist);
			// 이미 대기열에 존재한다면
			if (isExist) {
				// 대기열에 존재하지만 이미 매칭된 리더의 파티 탈퇴인지, 파티 중인지 판별
				CheckAlreadyUsedPstmt = conn
						.prepareStatement("select wouldUYN from matching_tbl where leader_id = ? and wouldUYN = 1");
				CheckAlreadyUsedPstmt.setString(1, userId);
				rs = CheckAlreadyUsedPstmt.executeQuery();

				// 매칭 되어 있지 않음.
				if (!rs.next()) {
					System.out.println("(DAO - isPossibleToWaitLeader)대기열에 이미 존재하지만, 매칭 중이진 않는 파티장");
					return true;
				}
			} else {
				System.out.println("대기열에 존재하지 않는 파티장");
				return true;
			}
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			return false;
		} finally {
			close(conn, CheckAlreadyUsedPstmt);
		}
		System.out.println("(DAO - isPossibleToWaitLeader)대기열 존재, 매칭 중(실패)");
		return false;
	}

	// 신청자가 wait테이블에 존재하는지 체크
	public boolean isExistWaiting(String userId, String tableName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = connect();
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

	// 일반 매칭 - 파티원 insert
	public void insertWaitMember(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("INSERT INTO member_wait_tbl(user_id) VALUES (?)");
			pstmt.setString(1, userId);
			pstmt.executeUpdate();

			System.out.println("(DAO - insertWaitMember)" + userId + " 파티원으로 insert");
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - insertWaitMember) 에러");
		} finally {
			close(conn, pstmt);
		}
	}

	// 일반 매칭 - 파티원 update
	public void updateWaitMember(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Timestamp now = new Timestamp(System.currentTimeMillis());

		try {
			conn = connect();
			pstmt = conn.prepareStatement(
					"update member_wait_tbl set is_wait = 1, waiting_time = ?, update_time = ?,  wouldUYN = 1 where user_id = ?");
			pstmt.setTimestamp(1, now);
			pstmt.setTimestamp(2, now);
			pstmt.setString(3, userId);
			pstmt.executeUpdate();
			System.out.println("(DAO - insertWaitMember)" + userId + " 파티원으로 update");
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - insertWaitMember) 에러");
		} finally {
			close(conn, pstmt);
		}
	}

	// 일반 매칭 - 파티장 insert
	public void insertWaitLeader(String userId, String netId, String netPassword, int HNOM) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = connect();
			pstmt = conn.prepareStatement(
					"insert into leader_wait_tbl(user_id ,max_number_of_member, net_id, net_password) values(?,?,?,?) ");
			pstmt.setString(1, userId);
			pstmt.setInt(2, HNOM);
			pstmt.setString(3, netId);
			pstmt.setString(4, netPassword);
			pstmt.executeUpdate();
			System.out.println("(DAO - insertWaitLeader)" + userId + " 파티장으로 insert");
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - insertWaitLeader) 에러");
		} finally {
			close(conn, pstmt);
		}
	}

	// 일반 매칭 - 파티장 update
	public void updateWaitLeader(String userId, String netId, String netPassword, int HNOM) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Timestamp now = new Timestamp(System.currentTimeMillis());

		try {
			conn = connect();
			pstmt = conn.prepareStatement(
					"update leader_wait_tbl set is_wait = 1, waiting_time = ?, update_time = ?,  wouldUYN = 1 where user_id = ?");
			pstmt.setTimestamp(1, now);
			pstmt.setTimestamp(2, now);
			pstmt.setString(3, userId);
			pstmt.executeUpdate();
			System.out.println("(DAO - updateWaitLeader)" + userId + " 파티장으로 update");
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - updateWaitLeader)에러");
		} finally {
			close(conn, pstmt);
		}
	}

	// 매칭 가능한 리더 찾기(맴버가 매칭 신청 시)
	public LeaderVO searchLeaderToMatching() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		LeaderVO leaderVO = null;
		try {
			conn = connect();
			pstmt = conn.prepareStatement(
					"select * from leader_wait_tbl where is_wait = 1 order by waiting_time asc limit 1");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				leaderVO = new LeaderVO();
				leaderVO.setUserId(rs.getString(1));
				leaderVO.setWait(rs.getBoolean(2));
				leaderVO.setNormal(rs.getBoolean(3));
				leaderVO.setDcPercent(rs.getInt(4));
				leaderVO.setMaxNumberOfMember(rs.getInt(5));
				leaderVO.setHowLongUse(rs.getInt(6));
				leaderVO.setNetId(rs.getString(7));
				leaderVO.setNetPassword(rs.getString(8));
				leaderVO.setWaitingTime(rs.getTimestamp(9));
				leaderVO.setCreatedTime(rs.getTimestamp(10));
				leaderVO.setUpdateTime(rs.getTimestamp(11));
				leaderVO.setWouldUYN(rs.getBoolean(12));
			}
			return leaderVO;

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - searchLeaderToMatching)에러");
		} finally {
			close(conn, pstmt);
		}
		return leaderVO;
	}

	// 매칭 가능한 맴버 찾기(리더가 매칭 신청 시)
	public ArrayList<MemberVO> searchMemberToMatching() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberVO> al = new ArrayList<MemberVO>();
		MemberVO memberVO = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement(
					"select * from member_wait_tbl where is_wait = 1 order by waiting_time asc limit 3");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setUserId(rs.getString(1));
				memberVO.setWait(rs.getBoolean(2));
				memberVO.setWaitingTime(rs.getTimestamp(3));
				memberVO.setCreatedTime(rs.getTimestamp(4));
				memberVO.setUpdateTime(rs.getTimestamp(5));
				memberVO.setWouldUYN(rs.getBoolean(6));
				al.add(memberVO);
			}
			System.out.println("(DAO - searchMemberToMatching)찾은 대기 맴버 수" + al.size());
			return al;

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - searchMemberToMatching)에러");
		} finally {
			close(conn, pstmt);
		}
		return al;
	}

	// 매칭아이디 조회
	public int getMatchingId(MatchingVO matchingVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("select matching_id from matching_tbl where leader_id = ?");

			insertMatchingInfo(matchingVO);

			pstmt.setString(1, matchingVO.getLeader_id());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			} else {
				System.out.println("(DAO - getMatchingId) matching_id 값 없음.");
				return -1;
			}

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - getMatchingId) 에러");
		} finally {
			close(conn, pstmt);
		}
		return -1;
	}

	// 매칭테이블 데이터 삽입
	public void insertMatchingInfo(MatchingVO matchingVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		/*
		 * ResultSet rs = null; matchingVO.setLeader_id(userId);
		 * matchingVO.setDcPercent(0); matchingVO.setMaxNumberOfMember(headCount);
		 * matchingVO.setFull(isFull); matchingVO.setNormal(true);
		 * matchingVO.setHowLongUse(0); matchingVO.setNet_id(netId);
		 * matchingVO.setNet_id(netPassword);
		 */

		try {
			conn = connect();
			pstmt = conn.prepareStatement("insert into matching_tbl"
					+ "(leader_id, dc_percent, max_number_of_member, is_full, is_normal, how_long_use, net_id, net_password) "
					+ "values(?,?,?,?,?,?,?,?)");
			pstmt.setString(1, matchingVO.getLeader_id());
			pstmt.setInt(2, matchingVO.getDcPercent());
			pstmt.setInt(3, matchingVO.getMaxNumberOfMember());
			pstmt.setBoolean(4, matchingVO.isFull());
			pstmt.setBoolean(5, matchingVO.isNormal());
			pstmt.setInt(6, matchingVO.getHowLongUse());
			pstmt.setString(7, matchingVO.getNet_id());
			pstmt.setString(8, matchingVO.getNet_password());
			pstmt.execute();

			System.out.println("");
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - insertMatchingInfo)에러");
		} finally {
			close(conn, pstmt);
		}
	}

	// 파티 맴버 삽입
	public void insertPartyMember(int matchingId, String leaderId, MemberVO memberVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement(
					"insert into party_member_info_tbl(matching_id, member_id, " + "pay, leader_id) values(?,?,?,?)");

			pstmt.setInt(1, matchingId);
			pstmt.setString(2, memberVO.getUserId());
			pstmt.setString(3, "3650");
			pstmt.setString(4, leaderId);

			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - insertPartyMember)에러");
		} finally {
			close(conn, pstmt);
		}
	}

	// 파티 맴버 삽입 (v2)
	public void insertPartyMember(int matchingId, String leaderId, String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement(
					"insert into party_member_info_tbl(matching_id, member_id, " + "pay, leader_id) values(?,?,?,?)");

			pstmt.setInt(1, matchingId);
			pstmt.setString(2, userId);
			pstmt.setString(3, "3650");
			pstmt.setString(4, leaderId);

			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - insertPartyMember(v2))에러");
		} finally {
			close(conn, pstmt);
		}
	}

	// member_wait안에 is_wait데이터 false로 변환
	public void updateIswaitToFalseInMemberWait(MemberVO memberVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("update member_wait_tbl set is_wait = 0, wouldUYN = 0 where user_id = ?");
			pstmt.setString(1, memberVO.getUserId());
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - updateIswaitToFalseInMemberWait)에러");
		} finally {
			close(conn, pstmt);
		}
	}

	// leader_wait안에 is_wait데이터 false로 변환
	public void updateIswaitToFalseInLeaderWait(String leaderId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("update leader_wait_tbl set is_wait = 0, wouldUYN = 0 where user_id = ?");
			pstmt.setString(1, leaderId);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - updateIswaitToFalseInLeaderWait)에러");
		} finally {
			close(conn, pstmt);
		}
	}

	// 매칭 중인 파티 중 빈자리가 있는지 확인
	public MatchingVO canIJoinYourMatching() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MatchingVO matchingVO = null;
		try {
			conn = connect();
			// 일단, 웨이팅시간으로
			pstmt = conn
					.prepareStatement("select * from matching_tbl where is_full = false order by waiting_time limit 1");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				matchingVO = new MatchingVO();
				matchingVO.setMatching_id(rs.getInt(1));
				matchingVO.setLeader_id(rs.getString(2));
				matchingVO.setDcPercent(rs.getInt(3));
				matchingVO.setMaxNumberOfMember(rs.getInt(4));
				matchingVO.setFull(rs.getBoolean(5));
				matchingVO.setNormal(rs.getBoolean(6));
				matchingVO.setHowLongUse(rs.getInt(7));
				matchingVO.setNet_id(rs.getString(8));
				matchingVO.setNet_password(rs.getString(9));
				matchingVO.setWaitingTime(rs.getTimestamp(10));
				matchingVO.setCreatedTime(rs.getTimestamp(11));
				matchingVO.setUpdateTime(rs.getTimestamp(12));
				matchingVO.setWouldUYN(rs.getBoolean(13));
				return matchingVO;
			}

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - canIJoinYourMatching)에러");
		} finally {
			close(conn, pstmt);
		}
		return matchingVO;
	}

	// 현재 매칭테이블에 매칭된 파티원 수
	public int getPresentMatchingMember(int matchingId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("select count(*)" + "from matching_tbl m join party_member_info_tbl p"
					+ "on m.leader_id = p.leader_id" + "where m.matching_id = ? and p.wouldUYN = true");
			pstmt.setInt(1, matchingId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - getPresentMatchingMember)에러");
		} finally {
			close(conn, pstmt);
		}
		return 0;
	}

	// 파티원 수가 다 찼을 때
	public void setIsFullTrue(int matchingId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("update matching_tbl set is_full = true where matching_id = ?");

			pstmt.executeUpdate();

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - setIsFullFalse)에러");
		} finally {
			close(conn, pstmt);
		}
	}

	// member_wait안에 is_wait데이터 false로 삽입
	public void insertIswaitToFalseInMemberWait(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("insert into member_wait_tbl(user_id, is_wait, wouldUYN) values(?, 0, 0)");
			pstmt.setString(1, userId);
			pstmt.executeUpdate();

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
			System.out.println("(DAO - insertIswaitToFalseInMemberWait)에러");
		} finally {
			close(conn, pstmt);
		}
	}
}
