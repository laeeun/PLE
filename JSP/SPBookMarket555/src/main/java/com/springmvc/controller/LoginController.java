package com.springmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springmvc.repository.BookRepositoryImpl;

@Controller
public class LoginController {
	public static Logger logger = LoggerFactory.getLogger(LoginController.class);
	@GetMapping("/login")
	public String login() {
		logger.info("🔐 [login()] 로그인 페이지 진입");
		return "login";
	}
	
	@GetMapping("/loginfailed")
	public String loginerror(Model model) {
		logger.info("❌ 로그인 실패! loginerror() 진입");
		logger.debug("📦 Model 데이터: {}", model.asMap());
		model.addAttribute("error", "true");
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		logger.info("👋 로그아웃 요청 들어옴 - logout() 메서드 실행");
		return "login";
	}
}
