package com.kyoni.plog.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kyoni.plog.service.UserService;
import com.kyoni.plog.vo.UserVO;

@Controller
@RequestMapping(path = "/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/***
	 * login 페이지 호출
	 * @return
	 */
	@GetMapping("/login")
	public String loginform() {
		logger.info("get login");
		return "login/login";
	}
	
	@PostMapping("/login")
	public String loginSuccess() {
		System.out.println("로그인 완료");
		return "/main";
	}
	
	/***
	 * login 에러시 호출
	 * @param loginError
	 * @param model
	 * @return
	 */
	@PostMapping("/loginerror")
	public String loginerror(@RequestParam("login_error") String loginError, Model model) {
		model.addAttribute("loginErrorType", loginError);
		return "login/login";
	}
	
	/***
	 * sign up 페이지 호출
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	@GetMapping("/register")
	public String register(HttpServletRequest request, HttpServletResponse response, Model model) throws NoSuchAlgorithmException, InvalidKeySpecException, ServletException, IOException {
		userService.createKeyPair(request, response, model);
		return "login/register";
	}
	
	/***
	 * sign up 실행
	 * @param userVO
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws UnsupportedEncodingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@PostMapping("/register")
	public String register(UserVO userVO, Model model, HttpSession session) throws ServletException, IOException {
		return userService.register(userVO, model, session);
	}

	/***
	 * 비밀번호 찾기 페이지 호출
	 * @return
	 */
	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "login/forgotPassword";
	}
}