package com.kyoni.plog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kyoni.plog.service.security.MemberService;

@Controller
@RequestMapping(path = "/user")
public class UserController {
	// 스프링 컨테이너가 생성자를 통해 자동으로 주입한다.
	private final MemberService memberService;

	public UserController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/login")
	public String loginform() {
		return "/login";
	}

	@RequestMapping("/loginerror")
	public String loginerror(@RequestParam("login_error") String loginError, Model model) {
		model.addAttribute("loginErrorType", loginError);
		return "/login";
	}
}