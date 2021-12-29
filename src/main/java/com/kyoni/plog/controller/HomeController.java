package com.kyoni.plog.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kyoni.plog.service.TestService;
import com.kyoni.plog.vo.TestVO;

@Controller
public class HomeController {
	
	private static final Logger logger = LogManager.getLogger(HomeController.class);
	
	@Autowired
	private TestService testService;
	
	@GetMapping(value = "/")
	public String main() {
		return "main";
	}
	
	@GetMapping(value = "/test")
	public String home(Model model) {
		logger.info("HomeController log Info Test");
		logger.debug("HomeController log Debug Test");
		
		TestVO vo = new TestVO();
		vo.setId("jkh");
		vo.setName("정기현");
		model.addAttribute("testModel", vo);
		
		return "test";
	}
	
	@PostMapping(value = "/insertTest")
	public String inputTest(TestVO testVO) {
		testService.insert(testVO);
		return "redirect:/test";
	}

}
