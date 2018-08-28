package com.mxp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mxp.beans.UserInfo;
import com.mxp.service.UserInfoService;
@Controller
public class UserInfoController {
	@Autowired
	public UserInfoService userInfoService;
	@RequestMapping("/show.action")
	public ModelAndView listUserInfo(){
		System.out.println("½øÈë¿ØÖÆÆ÷");
		ModelAndView mav = new ModelAndView();
		/*List<UserInfo> list = userInfoService.list();
		mav.addObject("list", list);*/
		UserInfo userInfo = userInfoService.get(1);
		mav.addObject("userInfo", userInfo);
		mav.setViewName("show");
		return mav;
	}
}
