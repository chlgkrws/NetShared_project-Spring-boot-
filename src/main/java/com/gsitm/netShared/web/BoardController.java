package com.gsitm.netshared.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gsitm.netshared.dto.BoardVO;
import com.gsitm.netshared.dto.Page;
import com.gsitm.netshared.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	// 글 상세보기
	@GetMapping("/view")
	public ModelAndView view(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer boardId) {

		String starRate = "";

		BoardVO boardVO = boardService.getView(boardId);
		request.setAttribute("board", boardVO);

		for (int i = 0; i < boardVO.getStarRate(); i++) {
			starRate += "★";
		}

		modelAndView.addObject("board", boardVO);
		modelAndView.addObject("star", starRate);
		modelAndView.setViewName("board/post");
		return modelAndView;
	}

	// 게시물 리스트
	@GetMapping("/list")
	public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer num, @RequestParam(required = false) String userid) {
		// userid -> userId로 바꾸기! $$$$$$$
		String userId = userid;
		Page page = new Page();
		page.setNum(num);
		page.setCount(boardService.getBoardCount());

		ArrayList<BoardVO> list;
		if (userId == null) {
			list = boardService.getBoardList(page.getDisplayPost(), page.getPostNum(), null); // 전체보기
			modelAndView.addObject("unCheck", true);
			modelAndView.addObject("check", false);
		} else {
			list = boardService.getBoardList(page.getDisplayPost(), page.getPostNum(), userId); // 내글보기
			modelAndView.addObject("check", true);
			modelAndView.addObject("unCheck", false);
		}

		modelAndView.addObject("board", new BoardVO());
		modelAndView.addObject("list", list);
		modelAndView.addObject("select", num);
		modelAndView.addObject("page", page);
		modelAndView.setViewName("board/community");
		return modelAndView;
	}

	// 글 삭제하기
	@PostMapping("/delete")
	public ModelAndView delete(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer boardId) {

		boardService.boardDelete(boardId);

		modelAndView.setViewName("/board/list?num=1");
		return modelAndView;
	}

	// 글 작성하기
	@PostMapping("/write")
	public ModelAndView write(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,
			@RequestParam String title, @RequestParam String star, @RequestParam String genre,
			@RequestParam Integer boardId, @RequestParam String modal_content, @RequestParam String id,
			@RequestParam String username, ) {
		// 파라미터 명 수정

		// 글쓰기인지 수정인지 체크
		boolean isModify = request.getParameter("valid").equals("true") ? true : false;
		int starRate = Integer.parseInt(star);
		BoardVO boardVO = new BoardVO(); // BoardVO에 파라미터로 받은 데이터를 넣어줌
		boardVO.setUserId(id);
		boardVO.setWriter(username);
		boardVO.setTitle(title);
		boardVO.setGenre(genre);
		boardVO.setStarRate(starRate);
		boardVO.setContent(modal_content);
		boardVO.setValid(true);

		if (isModify) {
			String writer = request.getParameter("writer");
			boardVO.setBoardId(boardId);
			boardVO.setWriter(writer);
			boardService.boardUpdate(boardVO);
		} else {
			boardService.boardWrite(boardVO);
		}
		modelAndView.setViewName("redirect:/board/list?num=1");
		return modelAndView;
	}


}
