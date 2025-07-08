package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springmvc.domain.Member;


@Controller
public class SignUpController {
	
  @GetMapping("/signUp")
  public String signUpForm(Model model){
	  model.addAttribute("signUp", new Member()); // 이게 있어야 오류 안 남
	  return "signUp"; // signUp.jsp
	  //@애노테이션 써서 바로 해도된다.
  }
}
