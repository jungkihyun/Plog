package com.kyoni.plog.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
	
	private static final Logger logger = LogManager.getLogger(HomeController.class);
	
	@GetMapping(value = "/")
	public String main() {
		return "main";
	}
}
