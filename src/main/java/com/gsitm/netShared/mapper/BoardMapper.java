package com.gsitm.netShared.mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.gsitm.netShared.dto.BoardVO;

public class BoardMapper {

	private static BoardMapper dao = new BoardMapper();

	private BoardMapper() {
	}

	public static BoardMapper getInstance() {
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

	// 글 작성
	public void boardWrite(BoardVO boardVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement(
					"INSERT INTO `it1457`.`board_tbl` (`user_id`, `writer`, `title`, `content`, `genre`, `star_rate`) VALUES (?,?,?,?,?,?)");
			pstmt.setString(1, boardVO.getUserId());
			pstmt.setString(2, boardVO.getWriter());
			pstmt.setString(3, boardVO.getTitle());
			pstmt.setString(4, boardVO.getContent());
			pstmt.setString(5, boardVO.getGenre());
			pstmt.setInt(6, boardVO.getStarRate());
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}
	}

	// 글 수정
	public void boardUpdate(BoardVO boardVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement(
					"Update `it1457`.`board_tbl` set title = ?, content = ?, genre = ?, star_rate =? where board_id = ?");
			pstmt.setString(1, boardVO.getTitle());
			pstmt.setString(2, boardVO.getContent());
			pstmt.setString(3, boardVO.getGenre());
			pstmt.setInt(4, boardVO.getStarRate());
			pstmt.setInt(5, boardVO.getBoardId());
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}
	}

	// 글 삭제
	public void boardDelete(int boardId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("Delete from `it1457`.`board_tbl` where board_id = ?");
			pstmt.setInt(1, boardId);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}
	}

	// 글 리스트 조회
	public ArrayList<BoardVO> getBoardList(int displayPost, int postNum) {
		ArrayList<BoardVO> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		BoardVO board = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("select * from board_tbl order by board_id desc limit ?, ?");
			pstmt.setInt(1, displayPost);
			pstmt.setInt(2, postNum);
			rs = pstmt.executeQuery();
			System.out.println(rs.getFetchSize());
			while (rs.next()) {
				if (!rs.getBoolean(9)) {
					continue;
				}

				board = new BoardVO();
				board.setBoardId(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setTitle(rs.getString(4));
				board.setContent(rs.getString(5));
				board.setCreatedTime(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp(6)));
				board.setRecommend(rs.getInt(7));
				board.setGenre(rs.getString(8));
				board.setValid(rs.getBoolean(9));
				list.add(board);
			}

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt, rs);
		}

		return list;
	}

	// 글 리스트 조회(내글보기 다형성)
	public ArrayList<BoardVO> getBoardList(int displayPost, int postNum, String userId) {
		ArrayList<BoardVO> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		BoardVO board = null;

		try {
			conn = connect();
			pstmt = conn
					.prepareStatement("select * from board_tbl where user_id = ? order by board_id desc limit ?, ?");
			pstmt.setString(1, userId);
			pstmt.setInt(2, displayPost);
			pstmt.setInt(3, postNum);
			rs = pstmt.executeQuery();
			System.out.println(rs.getFetchSize());
			while (rs.next()) {
				if (!rs.getBoolean(9)) {
					continue;
				}

				board = new BoardVO();
				board.setBoardId(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setTitle(rs.getString(4));
				board.setContent(rs.getString(5));
				board.setCreatedTime(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp(6)));
				board.setRecommend(rs.getInt(7));
				board.setGenre(rs.getString(8));
				board.setValid(rs.getBoolean(9));
				list.add(board);
			}

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt, rs);
		}

		return list;
	}

	// 글 상세보기
	public BoardVO getView(int boardId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		BoardVO board = null;
		ResultSet rs = null;
		try {
			conn = connect();
			pstmt = conn.prepareStatement("select * from board_tbl where board_id = ?");
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			rs.next();
			board = new BoardVO();
			board.setBoardId(rs.getInt(1));
			board.setUserId(rs.getString(2));
			board.setWriter(rs.getString(3));
			board.setTitle(rs.getString(4));
			board.setContent(rs.getString(5));
			board.setCreatedTime(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp(6)));
			board.setRecommend(rs.getInt(7));
			board.setGenre(rs.getString(8));
			board.setValid(rs.getBoolean(9));
			board.setStarRate(rs.getInt(10));

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}

		return board;
	}

	// 페이지를 위한 게시물 갯수 카운트
	public int getBoardCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		BoardVO board = null;
		ResultSet rs = null;
		try {
			conn = connect();
			pstmt = conn.prepareStatement("select count(board_id) from board_tbl");
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}
		// -1일 경우 에러
		System.out.println("getBoardCount 에러");
		return -1;
	}

	// 페이지를 위한 게시물 갯수 카운트(내글보기
	public int getBoardCount(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		BoardVO board = null;
		ResultSet rs = null;
		try {
			conn = connect();
			pstmt = conn.prepareStatement("select count(board_id) from board_tbl where user_id = ?");
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}
		// -1일 경우 에러
		System.out.println("getBoardCount 에러");
		return -1;
	}

	// 추천한 계정 찾기
	public int checkLike(int boardId, String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		try {
			conn = connect();
			pstmt = conn.prepareStatement("select * from board_like_tbl where board_id = ? and liked_user_id = ?");
			pstmt.setInt(1, boardId);
			pstmt.setString(2, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception ex) {
			System.out.println("BoardDAO checkLike 메소드 에러");
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}
		// -1일 경우 에러
		System.out.println("BoardDAO checkLike 메소드 에러");
		return 0;
	}

	// 추천한 계정 추가
	public void insertLikedUser(int boardId, String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("INSERT INTO board_like_tbl VALUES (?,?)");
			pstmt.setInt(1, boardId);
			pstmt.setString(2, userId);

			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}
	}

	// 추천한 계정 삭제
	public void deleteLikedUser(int boardId, String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn.prepareStatement("delete from board_like_tbl where board_id = ? and liked_user_id = ?");
			pstmt.setInt(1, boardId);
			pstmt.setString(2, userId);

			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}
	}

	// 추천수 증가
	public void updateLike(int boardId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn
					.prepareStatement("Update `it1457`.`board_tbl` set recommend = recommend + 1 where board_id = ?");
			pstmt.setInt(1, boardId);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}
	}

	// 추천수 감소
	public void updateLikeDown(int boardId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = connect();
			pstmt = conn
					.prepareStatement("Update `it1457`.`board_tbl` set recommend = recommend - 1 where board_id = ?");
			pstmt.setInt(1, boardId);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("오류 발생 : " + ex);
		} finally {
			close(conn, pstmt);
		}
	}

}
