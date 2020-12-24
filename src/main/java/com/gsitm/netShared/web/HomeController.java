package com.gsitm.netshared.web;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gsitm.netshared.dto.UserVO;
import com.gsitm.netshared.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public ModelAndView view(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {

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

		request.setAttribute("userVO", userVO);
		modelAndView.setViewName("home/my_page");

		return modelAndView;
	}

	@GetMapping("/intro")
	public ModelAndView intro(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		modelAndView.setViewName("home/page_intro");
		request.getSession().setAttribute("username", "최학준");
		request.getSession().setAttribute("id", "chlgkrws");
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
