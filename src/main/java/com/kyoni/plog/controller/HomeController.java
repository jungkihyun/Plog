package com.kyoni.plog.controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.mapper.UserMapper;
import com.kyoni.plog.service.security.SessionUser;

@Controller
public class HomeController {
	
	private static final Logger logger = LogManager.getLogger(HomeController.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping(value = "/")
	public String main(HttpSession session, Model model) {
		SessionUser su = (SessionUser) session.getAttribute("user");
		
		if(su != null) {
			model.addAttribute("user", userMapper.getUser(su.getEmail()));
		}
		return "main";
	}
}
