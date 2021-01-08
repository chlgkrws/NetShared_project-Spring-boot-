package com.gsitm.netshared.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gsitm.netshared.mapper.InsertMapper;
import com.gsitm.netshared.vo.User;

@Controller
@RequestMapping("/insert")
public class InsertController {

	@Autowired
	public InsertMapper insertMapper;

	@RequestMapping("/user")
	public ModelAndView user(ModelAndView modelAndView, HttpServletRequest request) throws ParseException {
		String line = null;
		File locationFile = new File("C:/Users/cgw981/Desktop/팀프로젝트/teamProjectCSV/user.csv");
		List<String[]> locationList = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(locationFile));
			while((line = in.readLine()) != null ) {
				String[] arr = line.split("@");
				locationList.add(arr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ho");

		int idx = 0;
		ArrayList<User> userList = new ArrayList<User>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0; i < locationList.size(); i++) {
			User userVO = new User();
			userVO.setEmpNo(locationList.get(i)[idx++]);
			userVO.setUserName(locationList.get(i)[idx++]);
			userVO.setBirth(dateFormat.parse(locationList.get(i)[idx++]));
			userVO.setOriginDept(locationList.get(i)[idx++]);
			userVO.setCurrentDept(locationList.get(i)[idx++]);
			userVO.setHireDate(dateFormat.parse(locationList.get(i)[idx++]));
			userVO.setExpTime(locationList.get(i)[idx++]);
			userVO.setCurrentJob(locationList.get(i)[idx++]);
			userVO.setCurrentProject(locationList.get(i)[idx++]);
			userVO.setJobGoal1(locationList.get(i)[idx++]);
			userVO.setJobGoal2(locationList.get(i)[idx++]);
			userVO.setJobGoal3(locationList.get(i)[idx++]);
			userVO.setDomainGoal1(locationList.get(i)[idx++]);
			userVO.setDomainGoal2(locationList.get(i)[idx++]);
			userVO.setDomainGoal3(locationList.get(i)[idx++]);
			userVO.setEduGoal1(locationList.get(i)[idx++]);
			userVO.setEduGoal2(locationList.get(i)[idx++]);
			userVO.setEduGoal3(locationList.get(i)[idx++]);
			userVO.setCareerGoal1(locationList.get(i)[idx++]);
			userVO.setCareerGoal2(locationList.get(i)[idx++]);
			userVO.setCareerGoal3(locationList.get(i)[idx++]);
			idx = 0;
			insertMapper.insertUser(userVO);
			//데이터 삽입
		}

		modelAndView.setViewName("redirect:/");
		return modelAndView;
	}

}
