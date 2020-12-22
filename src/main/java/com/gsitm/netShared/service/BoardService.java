package com.it1457.service;

import java.util.ArrayList;

import com.it1457.dao.BoardDAO;
import com.it1457.vo.BoardVO;

public class BoardService {
	private static BoardService service = new BoardService();
	public BoardDAO dao = BoardDAO.getInstance();

	private BoardService() {
	}

	public static BoardService getInstance() {
		return service;
	}

	// 글 작성
	public void boardWrite(BoardVO boardVO) {
		dao.boardWrite(boardVO);
	}

	// 글 수정
	public void boardUpdate(BoardVO boardVO) {
		dao.boardUpdate(boardVO);
	}

	// 글 삭제
	public void boardDelete(int boardId) {
		dao.boardDelete(boardId);
	}

	// 글 리스트 조회
	public ArrayList<BoardVO> getBoardList(int displayPost, int postNum) {
		return dao.getBoardList(displayPost, postNum);
	}
	
	// 내글 조회
	public ArrayList<BoardVO> getBoardList(int displayPost, int postNum, String userId) {
		return dao.getBoardList(displayPost, postNum, userId);
	}

	// 글 상세보기
	public BoardVO getView(int boardId) {
		return dao.getView(boardId);
	}

	// 게시글 갯수
	public int getBoardCount() {
		return dao.getBoardCount();
	}

	// 추천한 계정 조회
	public int checkLike(int boardId, String userId) {
		return dao.checkLike(boardId, userId);
	}

	// 추천한 계정 삽입
	public void insertLikedUser(int boardId, String userId) {
		dao.insertLikedUser(boardId, userId);
	}

	// 추천한 계정 삭제
	public void deleteLikedUser(int boardId, String userId) {
		dao.deleteLikedUser(boardId, userId);
	}

	// 추천수 증가
	public void updateLike(int boardId) {
		dao.updateLike(boardId);
	}

	// 추천수 감소
	public void updateLikeDown(int boardId) {
		dao.updateLikeDown(boardId);
	}
	

	/*
	 * //FrontController에 있는 boardHm에 데이터 가져오기 **데이터의 흐름을 보기 위해 짠 코드** public
	 * LinkedHashMap<Integer, BoardVO> getData() { setDate(); FrontController.flag =
	 * false; return FrontController.boardHm; }
	 * 
	 * public void setDate() { BoardVO pub1 = new BoardVO(); BoardVO pub2 = new
	 * BoardVO(); BoardVO pub3 = new BoardVO(); BoardVO pub4 = new BoardVO();
	 * 
	 * //게시물 1 pub1.setPostId(FrontController.count++); pub1.setId("chlgkrws");
	 * pub1.setTitle("커플끼리 보기 좋은 영화! 365일"); pub1.setWriter("최학준"); pub1.
	 * setContent("직장에서 성공 가도를 달리고 있지만 연인과의 관계에는 열정을 잃은지 오래인 라우라(안나 마리아 시에클루츠카)는 <br>남자친구와\r\n"
	 * +
	 * "						함께 시칠리아로 여행을 떠났다가 마피아 보스에게 납치당하게 된다.<br> 그녀를 납치한 것은 마시모(미켈레\r\n"
	 * +
	 * "						모로네). 그는 5년 전, 자신의 눈앞에서 아버지를 잃고 자신 또한 죽을 뻔한 위기를 겪었는데, 그 순간 자신의 눈앞에\r\n"
	 * +
	 * "						아른거렸던 그녀가 실존할 것이라며 찾고 있던 것이었다.<br> 그 여자가 바로 라우라인 것! 그는 라우라에게\r\n"
	 * +
	 * "						이러한 설명과 함께 1년의 시간을 주고 그 안에 자신과 사랑에 빠지지 않으면 돌려보내 주겠다는 제안을 한다."
	 * ); pub1.setStarRate(5); pub1.setRecommend(150); pub1.setGenre("로맨스");
	 * pub1.setOpen(true); pub1.setDate("2020.2.14");
	 * 
	 * 
	 * //게시물 2 pub2.setPostId(FrontController.count++); pub2.setId("papqw");
	 * pub2.setTitle("드라마 추천 살아있다!"); pub2.setWriter("권지은"); pub2.
	 * setContent("직장에서 성공 가도를 달리고 있지만 연인과의 관계에는 열정을 잃은지 오래인 라우라(안나 마리아 시에클루츠카)는 <br>남자친구와\r\n"
	 * +
	 * "						함께 시칠리아로 여행을 떠났다가 마피아 보스에게 납치당하게 된다.<br> 그녀를 납치한 것은 마시모(미켈레\r\n"
	 * +
	 * "						모로네). 그는 5년 전, 자신의 눈앞에서 아버지를 잃고 자신 또한 죽을 뻔한 위기를 겪었는데, 그 순간 자신의 눈앞에\r\n"
	 * +
	 * "						아른거렸던 그녀가 실존할 것이라며 찾고 있던 것이었다.<br> 그 여자가 바로 라우라인 것! 그는 라우라에게\r\n"
	 * +
	 * "						이러한 설명과 함께 1년의 시간을 주고 그 안에 자신과 사랑에 빠지지 않으면 돌려보내 주겠다는 제안을 한다.다."
	 * ); pub2.setStarRate(4); pub2.setRecommend(42); pub2.setGenre("로맨스");
	 * pub2.setOpen(true); pub2.setDate("2020.4.14");
	 * 
	 * 
	 * //게시물 3 pub3.setPostId(FrontController.count++); pub3.setId("vesgs123");
	 * pub3.setTitle("스파이더맨 뉴 유니버스!!"); pub3.setWriter("김일환"); pub3.
	 * setContent("직장에서 성공 가도를 달리고 있지만 연인과의 관계에는 열정을 잃은지 오래인 라우라(안나 마리아 시에클루츠카)는 <br>남자친구와\r\n"
	 * +
	 * "						함께 시칠리아로 여행을 떠났다가 마피아 보스에게 납치당하게 된다.<br> 그녀를 납치한 것은 마시모(미켈레\r\n"
	 * +
	 * "						모로네). 그는 5년 전, 자신의 눈앞에서 아버지를 잃고 자신 또한 죽을 뻔한 위기를 겪었는데, 그 순간 자신의 눈앞에\r\n"
	 * +
	 * "						아른거렸던 그녀가 실존할 것이라며 찾고 있던 것이었다.<br> 그 여자가 바로 라우라인 것! 그는 라우라에게\r\n"
	 * +
	 * "						이러한 설명과 함께 1년의 시간을 주고 그 안에 자신과 사랑에 빠지지 않으면 돌려보내 주겠다는 제안을 한다."
	 * ); pub3.setStarRate(4); pub3.setRecommend(14); pub3.setGenre("로맨스");
	 * pub3.setOpen(true); pub3.setDate("2020.3.14");
	 * 
	 * 
	 * //게시물 4 pub4.setPostId(FrontController.count++); pub4.setId("papqw");
	 * pub4.setTitle("검사외전"); pub4.setWriter("이진호"); pub4.
	 * setContent("직장에서 성공 가도를 달리고 있지만 연인과의 관계에는 열정을 잃은지 오래인 라우라(안나 마리아 시에클루츠카)는 <br>남자친구와\r\n"
	 * +
	 * "						함께 시칠리아로 여행을 떠났다가 마피아 보스에게 납치당하게 된다.<br> 그녀를 납치한 것은 마시모(미켈레\r\n"
	 * +
	 * "						모로네). 그는 5년 전, 자신의 눈앞에서 아버지를 잃고 자신 또한 죽을 뻔한 위기를 겪었는데, 그 순간 자신의 눈앞에\r\n"
	 * +
	 * "						아른거렸던 그녀가 실존할 것이라며 찾고 있던 것이었다.<br> 그 여자가 바로 라우라인 것! 그는 라우라에게\r\n"
	 * +
	 * "						이러한 설명과 함께 1년의 시간을 주고 그 안에 자신과 사랑에 빠지지 않으면 돌려보내 주겠다는 제안을 한다."
	 * ); pub4.setStarRate(4); pub4.setRecommend(21); pub4.setGenre("로맨스");
	 * pub4.setOpen(true); pub4.setDate("2020.5.14");
	 * 
	 * 
	 * FrontController.boardHm.put(pub1.getPostId(), pub1);
	 * FrontController.boardHm.put(pub2.getPostId(), pub2);
	 * FrontController.boardHm.put(pub3.getPostId(), pub3);
	 * FrontController.boardHm.put(pub4.getPostId(), pub4); }
	 */
}
