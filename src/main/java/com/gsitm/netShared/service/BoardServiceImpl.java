package com.gsitm.netshared.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsitm.netshared.dto.BoardVO;
import com.gsitm.netshared.mapper.BoardMapper;




@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	public BoardMapper boardMapper;


	// 글 작성
	public void boardWrite(BoardVO boardVO) {
		boardMapper.boardWrite(boardVO);
	}

	// 글 수정
	public void boardUpdate(BoardVO boardVO) {
		boardMapper.boardUpdate(boardVO);
	}

	// 글 삭제
	public void boardDelete(int boardId) {
		boardMapper.boardDelete(boardId);
	}

	// 글 리스트 조회
	public ArrayList<BoardVO> getBoardList(int displayPost, int postNum) {
		return boardMapper.getBoardList(displayPost, postNum);
	}

	// 내글 조회
	public ArrayList<BoardVO> getBoardList(int displayPost, int postNum, String userId) {
		return boardMapper.getBoardList(displayPost, postNum, userId);
	}

	// 글 상세보기
	public BoardVO getView(int boardId) {
		return boardMapper.getView(boardId);
	}

	// 게시글 갯수
	public int getBoardCount() {
		return boardMapper.getBoardCount();
	}

	// 추천한 계정 조회
	public int checkLike(int boardId, String userId) {
		return boardMapper.checkLike(boardId, userId);
	}

	// 추천한 계정 삽입
	public void insertLikedUser(int boardId, String userId) {
		boardMapper.insertLikedUser(boardId, userId);
	}

	// 추천한 계정 삭제
	public void deleteLikedUser(int boardId, String userId) {
		boardMapper.deleteLikedUser(boardId, userId);
	}

	// 추천수 증가
	public void updateLike(int boardId) {
		boardMapper.updateLike(boardId);
	}

	// 추천수 감소
	public void updateLikeDown(int boardId) {
		boardMapper.updateLikeDown(boardId);
	}


}
