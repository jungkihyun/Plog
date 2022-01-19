package com.kyoni.plog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kyoni.plog.service.security.MemberService;

@Controller
@RequestMapping(path = "/user")
public class UserController {
	
	@Autowired
	private MemberService memberService;

	@GetMapping("/login")
	public String loginform() {
		return "login/login";
	}
	
	@RequestMapping("/loginerror")
	public String loginerror(@RequestParam("login_error") String loginError, Model model) {
		model.addAttribute("loginErrorType", loginError);
		return "login/login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "login/register";
	}

	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "login/forgotPassword";
	}
}