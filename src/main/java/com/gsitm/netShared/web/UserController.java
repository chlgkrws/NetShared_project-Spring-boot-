package com.gsitm.netshared.web;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gsitm.netshared.dto.UserVO;
import com.gsitm.netshared.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 로그인 화면
	 * @param modelAndView
	 * @param request
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view(ModelAndView modelAndView, HttpServletRequest request) {

		modelAndView.setViewName("user/login");
		return modelAndView;
	}


	/**
	 * 로그인(패스워드 비교)
	 * @param modelAndView
	 * @param request
	 * @param id
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView modelAndView, HttpServletRequest request ,@RequestParam(required = false) String id, @RequestParam(required = false) String password) {
		UserVO userVO = userService.userSearch(id);
		if(userVO == null) {
			modelAndView.setViewName("layout/alert");
			modelAndView.addObject("msg", "아이디와 비번을 확인해주세요.");
			modelAndView.addObject("url", "/user/view");
			return modelAndView;
		}
		if (!userVO.isWouldUYN()) {
			/*
			 * HttpUtil.alertToJsp(request, response, "/views/login/login.jsp",
			 * "아이디와 비밀번호를 확인해주세요."); return;
			 */
			modelAndView.addObject("msg", "아이디와 비번을 확인해주세요.");
			modelAndView.addObject("url", "/user/view");
			modelAndView.setViewName("layout/alert");
		}else if (userVO.getPassword().equals(password)) {
			modelAndView.addObject("userVO", userVO);
			request.getSession().setAttribute("id", userVO.getUserId());
			request.getSession().setAttribute("username", userVO.getUserName());
			request.getSession().setAttribute("viewLeaderInfo", null);

			/*
			 * HttpUtil.forward(request, response, "views/login/login_session.jsp"); return;
			 */

			modelAndView.setViewName("redirect:/");
		} else {
			/*
			 * HttpUtil.alertToJsp(request, response, "/views/login/login.jsp",
			 * "아이디와 비밀번호를 확인해주세요."); return;
			 */
			modelAndView.addObject("msg", "아이디와 비번을 확인해주세요.");
			modelAndView.addObject("url", "/user/view");
			modelAndView.setViewName("layout/alert");
		}

		return modelAndView;
	}

	/**
	 * 로그아웃
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(ModelAndView modelAndView, HttpServletRequest request) {
		request.getSession().setAttribute("id", null);
		request.getSession().setAttribute("username", null);

		modelAndView.setViewName("redirect:/");
		return modelAndView;
	}

	/**
	 * 회원가입
	 * @param modelAndView
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView getSignUp(ModelAndView modelAndView, HttpServletRequest request) {

		modelAndView.setViewName("user/sign_up");
		return modelAndView;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView postSignUp(ModelAndView modelAndView, HttpServletRequest request,
			@RequestParam String id, @RequestParam String password, @RequestParam String username, @RequestParam String phone, @RequestParam String account) {
		UserVO userVO = new UserVO(); // UserVO에 회원가입창에서 받아온 데이터들을 넣음


		userVO.setUserId(id);
		userVO.setPassword(password);
		userVO.setUserName(username);
		userVO.setIdentity("000000");
		userVO.setPhone(phone);
		userVO.setAccount(account);

		userService.userInsert(userVO);

		modelAndView.setViewName("user/login");
		return modelAndView;
	}

	@RequestMapping("/checkpassword")
	public ModelAndView checkPassword(ModelAndView modelAndView, HttpServletRequest request,
			@RequestParam(value = "userid") String userId, @RequestParam String password) {

		UserVO userVO = userService.userSearch(userId);

		if(userVO.getPassword().equals(password)) {
			modelAndView.addObject("userVO", userVO);
			modelAndView.setViewName("user/modify_info");
		}else {
			modelAndView.addObject("msg", "비밀번호를 확인해주세요.");
			modelAndView.addObject("url", "/user/modify");
			modelAndView.setViewName("layout/alert");
		}

		return modelAndView;
	}

	/**
	 * get으로 회원정보 수정 요청 -> 비밀번호 요청 창
	 * @param modelAndView
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public ModelAndView getModify(ModelAndView modelAndView, HttpServletRequest request) {

		modelAndView.setViewName("user/check_password");
		return modelAndView;
	}

	/**
	 * post로 회원정보 수정 요청 -> 회원정보 update -> 홈 화면
	 * @param modelAndView
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public ModelAndView postModify(ModelAndView modelAndView, HttpServletRequest request,
			@RequestParam(value = "userid") String userId, @RequestParam String password, @RequestParam String phone, @RequestParam String account) {


		UserVO userVO = new UserVO();
		userVO.setUserId(userId);
		userVO.setPassword(password);
		userVO.setPhone(phone);
		userVO.setAccount(account);
		userVO.setUpdateTime((new Timestamp(System.currentTimeMillis())).toString());

		int updateCheck = userService.updateInfo(userVO);

		modelAndView.addObject("msg", "회원정보 수정에 성공헀습니다.");

		if(updateCheck == 0) {
			modelAndView.addObject("msg", "회원정보 수정에 실패헀습니다.");
		}

		modelAndView.addObject("url", "/");
		modelAndView.setViewName("layout/alert");
		return modelAndView;
	}

	@RequestMapping("/withdrawuser")
	public ModelAndView withrawUser(ModelAndView modelAndView, HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("id");

		userService.withdrawUser(userId);
		request.getSession().setAttribute("id",null);
		request.getSession().setAttribute("username",null);
		modelAndView.setViewName("redirect:/");
		return modelAndView;
	}

}
