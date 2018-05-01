package com.atsz.front.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.atguigu.common.utils.CookieUtils;
import com.atsz.front.service.HttpclientService;
import com.atsz.front.service.UserService;
import com.atsz.manager.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Value("ATSZ_TICKET")
	public String ATSZ_TICKET; 

	@Autowired
	UserService userService;

	@RequestMapping(value = "dologin", method = RequestMethod.POST)
	public ModelAndView doLogin(@RequestParam("loginname") String username,
			@RequestParam("nloginpwd") String password,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();

		try {

			String ticket = userService.doLogin(username, password);

			if (ticket == null) {
				// 登录失败
				mv.setViewName("login");
				return mv;
			}
			// 登录成功
			mv.setViewName("index");
			
			// 往浏览器中写入cookie，cookie共享
			CookieUtils.setCookie(request, response, ATSZ_TICKET, ticket,
					true);

			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.setViewName("login");
		return mv;
	}

	@RequestMapping(value = "doReg", method = RequestMethod.POST)
	public ModelAndView doReg(User user) {
		System.out.println(user.toString());

		return null;
	}

}
