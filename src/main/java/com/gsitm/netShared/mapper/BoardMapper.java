package com.gsitm.netshared.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gsitm.netshared.dto.BoardVO;

/**
 * 게시판 관련 매퍼
 * @author cgw981
 *
 */
@Repository
@Mapper
public interface BoardMapper {

	// 글 작성
	public void boardWrite(@Param("boardVO") BoardVO boardVO);

	// 글 수정
	public void boardUpdate(@Param("boardVO") BoardVO boardVO) throws Exception;

	// 글 삭제
	public void boardDelete(@Param("boardId") int boardId);

	// 글 리스트 조회
	public ArrayList<BoardVO> getBoardList(@Param("displayPost")int displayPost, @Param("postNum")int postNum);

	// 글 리스트 조회(내글보기 다형성)
	public ArrayList<BoardVO> getBoardList(@Param("displayPost") int displayPost, @Param("postNum") int postNum, @Param("userId")String userId);

	// 글 상세보기
	public BoardVO getView(@Param("boardId")int boardId);

	// 페이지를 위한 게시물 갯수 카운트
	public int getBoardCount();

	/**
	 * 페이지를 위한 게시물 갯수 카운트(내글보기)
	 * @param userId
	 * @return
	 */
	public int getBoardCount(String userId);

	/**
	 * 추천한 계정 찾기
	 * @param boardId
	 * @param userId
	 * @return
	 */
	public int checkLike(@Param("boardId")int boardId, @Param("userId")String userId);

	/**
	 * 추천한 계정 추가
	 * @param boardId
	 * @param userId
	 */
	public void insertLikedUser(@Param("boardId")int boardId, @Param("userId")String userId);

	/**
	 * 추천한 계정 삭제
	 * @param boardId
	 * @param userId
	 */
	public void deleteLikedUser(@Param("boardId")int boardId, @Param("userId")String userId);

	/**
	 * 추천수 증가
	 * @param boardId
	 */
	public void updateLike(@Param("boardId")int boardId);

	/**
	 * 추천수 감소
	 * @param boardId
	 */
	public void updateLikeDown(@Param("boardId")int boardId);
}
