package com.springmvc.controller; // 컨트롤러 패키지 선언

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller; // Spring MVC의 Controller 역할임을 선언
import org.springframework.ui.Model; // 뷰에 데이터를 전달하는 객체
import org.springframework.web.bind.annotation.GetMapping; // GET 요청 매핑용 어노테이션

import com.springmvc.repository.BookRepositoryImpl; // (현재 코드에서는 사용되지 않음)

@Controller // 이 클래스가 Controller 역할을 한다고 Spring에게 알림
public class LoginController {
	public static Logger logger = LoggerFactory.getLogger(LoginController.class); // 로그 출력을 위한 Logger 객체 선언

	@GetMapping("/login") // URL 요청 "/login"이 들어오면 실행되는 메서드
	public String login() {
		logger.info("🔐 [login()] 로그인 페이지 진입"); // 로그인 진입 로그 출력
		return "login"; // login.jsp로 이동 (View 이름 반환)
	}
	
	@GetMapping("/loginfailed") // URL 요청 "/loginfailed"가 들어오면 실행
	public String loginerror(Model model) {
		logger.info("❌ 로그인 실패! loginerror() 진입"); // 실패 로그
		logger.debug("📦 Model 데이터: {}", model.asMap()); // Model에 담긴 데이터 디버그 출력
		model.addAttribute("error", "true"); // 뷰에서 에러 표시를 위해 "error" 속성 추가
		return "login"; // 로그인 화면으로 다시 이동
	}
	
	@GetMapping("/logout") // "/logout" 요청이 들어오면 실행
	public String logout(Model model) {
		logger.info("👋 로그아웃 요청 들어옴 - logout() 메서드 실행"); // 로그아웃 로그 출력
		return "login"; // 로그아웃 후 login.jsp로 이동
	}
}
