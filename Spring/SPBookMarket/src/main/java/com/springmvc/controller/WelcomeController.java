package com.springmvc.controller; // 컨트롤러 클래스가 위치한 패키지

import org.slf4j.Logger; // 로그를 남기기 위한 Logger 인터페이스
import org.slf4j.LoggerFactory; // Logger 객체 생성을 위한 팩토리
import org.springframework.stereotype.Controller; // 이 클래스가 Spring MVC의 컨트롤러임을 나타냄
import org.springframework.ui.Model; // JSP로 데이터를 전달하기 위한 객체
import org.springframework.web.bind.annotation.RequestMapping; // URL 요청을 메서드에 매핑할 때 사용
import org.springframework.web.bind.annotation.RequestMethod; // HTTP 요청 방식을 명시하기 위한 enum

@Controller // 이 클래스가 컨트롤러임을 Spring에게 알림
public class WelcomeController {
	public static Logger logger = LoggerFactory.getLogger(WelcomeController.class); // 로그 출력을 위한 Logger 객체 생성

	WelcomeController() {
		logger.info("🚀 WelcomeController 객체 생성 완료!"); // 생성자에서 로그 출력: 서버 시작 시 객체 생성 확인용
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET) // /home 경로로 GET 요청이 들어오면 welcome() 메서드 실행
	public String welcome(Model model) {
		logger.info("👉 [welcome()] /home 요청 진입이닷!!"); // 로그 출력: 요청이 들어왔는지 확인용
		model.addAttribute("greeting", "Welcome to BookMarket"); // JSP에 전달할 인사말 메시지 추가
		model.addAttribute("strapline", "Welcome to Web Shopping Mall!"); // JSP에 전달할 부가 메시지 추가
		return "welcome"; // /WEB-INF/views/welcome.jsp로 forward됨
	}
}
