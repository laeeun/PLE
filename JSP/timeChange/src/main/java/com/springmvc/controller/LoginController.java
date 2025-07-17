package com.springmvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.domain.Member;
import com.springmvc.service.MemberService;

@Controller
public class LoginController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	
	@GetMapping("login")
	public String loginForm() {
		return "login";
	}
	
	@PostMapping("login")
	public String loginProcess(@RequestParam String member_id, @RequestParam String pw,
	                           HttpSession session, Model model) {

	    Member member = memberService.login(member_id);

	    if (member == null) {
	        model.addAttribute("error", "존재하지 않는 회원입니다.");
	        return "login";
	    }

	    if (!passwordEncoder.matches(pw, member.getPw())) {
	        model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
	        return "login";
	    }

	    session.setAttribute("loggedInUser", member);
	    return "redirect:/";
	}
	
	 // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 모든 세션 속성 제거
        return "home";
    }
    
}
