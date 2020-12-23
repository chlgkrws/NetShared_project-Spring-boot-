package com.gsitm.netshared.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public ModelAndView view(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
    	request.getSession().setAttribute("username", "최학준");
    	request.getSession().setAttribute("id", "chlgkrws");
        modelAndView.setViewName("home/home");
        return modelAndView;
    }

    @GetMapping("/mypage")
    public ModelAndView myPage(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
    	modelAndView.setViewName("home/my_page");
    	HttpSession session = request.getSession();

    	return modelAndView;
    }

    @GetMapping("/intro")
    public ModelAndView intro(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
    	modelAndView.setViewName("home/page_intro");
    	HttpSession session = request.getSession();

    	return modelAndView;
    }


}
