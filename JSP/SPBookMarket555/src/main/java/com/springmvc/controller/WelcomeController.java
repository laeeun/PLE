package com.springmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {
	public static Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	WelcomeController() {
		logger.info("🚀 WelcomeController 객체 생성 완료!");
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String welcome(Model model) {
		logger.info("👉 [welcome()] /home 요청 진입이닷!!");
		model.addAttribute("greeting", "Welcome to BookMarket");
		model.addAttribute("strapline", "Welcome to Web Shopping Mall!");
		return "welcome";
	}
}
