package com.gsitm.netShared.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.gsitm.netShared.dto.BoardVO;

@Service
public interface BoardService {
	// 글 작성
	public void boardWrite(BoardVO boardVO);

	// 글 수정
	public void boardUpdate(BoardVO boardVO);

	// 글 삭제
	public void boardDelete(int boardId);

	// 글 리스트 조회
	public ArrayList<BoardVO> getBoardList(int displayPost, int postNum);

	// 내글 조회
	public ArrayList<BoardVO> getBoardList(int displayPost, int postNum, String userId);

	// 글 상세보기
	public BoardVO getView(int boardId);

	// 게시글 갯수
	public int getBoardCount();

	// 추천한 계정 조회
	public int checkLike(int boardId, String userId);

	// 추천한 계정 삽입
	public void insertLikedUser(int boardId, String userId);

	// 추천한 계정 삭제
	public void deleteLikedUser(int boardId, String userId);

	// 추천수 증가
	public void updateLike(int boardId);

	// 추천수 감소
	public void updateLikeDown(int boardId);

}
