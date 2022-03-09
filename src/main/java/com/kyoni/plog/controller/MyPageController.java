package com.kyoni.plog.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kyoni.plog.service.UserService;
import com.kyoni.plog.service.security.MemberServiceImpl;
import com.kyoni.plog.service.security.SessionUser;
import com.kyoni.plog.vo.UserVO;

@Controller
@RequestMapping(path = "/user/mypage")
public class MyPageController {

private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MemberServiceImpl memberService;

	@Autowired
	private UserService userService;

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
	
	@PostMapping("/update")
	@ResponseBody
	public void infoUpdate(UserVO vo, HttpServletRequest request) throws IOException {
		userService.updateUserPicture(vo, request);
	}
	
//	@PostMapping("/update")
//	@ResponseStatus(HttpStatus.CREATED)
//	public List<String> upload(@RequestPart List<MultipartFile> files) throws Exception {
//		List<String> list = new ArrayList<>();
//		for (MultipartFile file : files) {
//			String originalfileName = file.getOriginalFilename();
//			File dest = new File("C:/Image/" + originalfileName);
//			file.transferTo(dest);
//			// TODO
//		}
//		return list;
//	}
	
}
