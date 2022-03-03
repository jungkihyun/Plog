package com.kyoni.plog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kyoni.plog.service.security.MemberServiceImpl;
import com.kyoni.plog.service.security.SessionUser;

@Controller
@RequestMapping(path = "/user/mypage")
public class MyPageController {

private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MemberServiceImpl memberService;

	@GetMapping("/info")
	public String infoPage(HttpSession session, Model model) {
		SessionUser su = (SessionUser) session.getAttribute("user");
		
		if(su != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("email", su.getEmail());
			map.put("oauthKey", su.getOauthKey());
			model.addAttribute("user", memberService.getUser(map));
		}
		return "mypage/info";
	}
	
}
