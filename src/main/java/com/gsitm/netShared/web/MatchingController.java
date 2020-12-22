package com.gsitm.netShared.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gsitm.netShared.service.MatchingService;

@Controller
@RequestMapping("/matching")
public class MatchingController {

	@Autowired
	private MatchingService matchingService;
}
