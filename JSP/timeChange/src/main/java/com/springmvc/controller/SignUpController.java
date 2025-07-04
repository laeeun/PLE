package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springmvc.domain.Member;

@Controller
public class SignUpController {
	
	public String signUp(@ModelAttribute Member member) {
		// 1. 전화번호 세 개 조합
		String fullPhone = member.getPhone1() + member.getPhone2() + member.getPhone3();
		// 2. 전제 전화번호를 하나로 저장 
		member.setPhone(fullPhone);
		
		System.out.println(member.getPhone());
		
		return "home";
	}
}
