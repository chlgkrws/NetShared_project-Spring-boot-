package com.gsitm.netshared.web;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gsitm.netshared.dto.LeaderInfoVO;
import com.gsitm.netshared.dto.LeaderVO;
import com.gsitm.netshared.dto.UserVO;
import com.gsitm.netshared.service.MatchingService;
import com.gsitm.netshared.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired(required = false)
	private UserService userService;

	@Autowired(required = false)
	private MatchingService matchingService;

	@GetMapping("/")
	public ModelAndView view(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {

		//세션값이 매칭 데이터 한번 조회한다음에, 모달로 알림 띄어주고, 그 다음부터 안뜨게 하고 마이페이지에서 해당 매칭아이디 비번 볼 수 있게 함.

		if(request.getSession().getAttribute("viewLeaderInfo") == null) {
			LeaderInfoVO leaderInfoVO = matchingService.getLeaderInfo((String)request.getSession().getAttribute("id"));
			System.out.println("hi isNew");
			if(leaderInfoVO != null) {
				System.out.println("hi not null");
				modelAndView.addObject("isNew", true);
				modelAndView.addObject("leaderInfoVO", leaderInfoVO);
				request.getSession().setAttribute("viewLeaderInfo", "not null");
			}
		}

		//빠른 매칭 대기열
		ArrayList<LeaderVO> leaderList = matchingService.getLeaderVOList();

		modelAndView.addObject("leaderList", leaderList);
		modelAndView.setViewName("home/home");
		return modelAndView;
	}

	@GetMapping("/mypage")
	public ModelAndView myPage(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {

		String userId = (String) request.getSession().getAttribute("id");
		UserVO userVO = userService.userSearch(userId);

		// 핸드폰 번호 가리기
		userVO.setPhone(encryptPhone(userVO.getPhone()));

		// 계좌 번호 가리기
		userVO.setAccount(encryptAccount(userVO.getAccount()));

		//매칭되어 있는 정보 가져오기
		LeaderInfoVO leaderInfoVO = matchingService.getLeaderInfo((String)request.getSession().getAttribute("id"));
		if(leaderInfoVO != null) {
			modelAndView.addObject("matched", true);
			modelAndView.addObject("leaderInfoVO", leaderInfoVO);
		}

		request.setAttribute("userVO", userVO);
		modelAndView.setViewName("home/my_page");

		return modelAndView;
	}

	@GetMapping("/intro")
	public ModelAndView intro(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		modelAndView.setViewName("home/page_intro");
		return modelAndView;
	}

	// 핸드폰 번호 가리기
	public String encryptPhone(String phone) {
		if (phone.length() == 11) { // 가운데 변수가 4자리일 때
			phone = phone.substring(0, 7); // 0101111
			phone = phone.substring(0, 3) + "-" + phone.substring(3); // 010-1111
		} else {
			phone = phone.substring(0, 6); // 010111
			phone = phone.substring(0, 3) + "-" + phone.substring(3);
		}
		phone += "-****";

		return phone;
	}

	// 계좌 번호 가리기
	public String encryptAccount(String account) { // 1234567890 -> 123*****90
		int len = account.length();
		int partLen = len / 2;
		int subLen = partLen / 2;

		String pattern = account.substring(partLen - subLen, partLen + subLen + 1);

		account = account.replace(pattern, String.join("", Collections.nCopies(partLen, "*")));

		return account;
	}

}
