package com.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.domain.Member;
import com.springmvc.service.MemberService;


@Controller
public class SignUpController {

    private final ForgotController forgotController;

    private final HomeController homeController;
 
	@Autowired	
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    SignUpController(HomeController homeController, ForgotController forgotController) {
        this.homeController = homeController;
        this.forgotController = forgotController;
    }
	
	@GetMapping("/signUp")
    public String signUpForm(Model model){
		model.addAttribute("signUp", new Member()); // 이게 있어야 오류 안 남
		System.out.println("회원가입 페이지로 이동");
		return "signUp"; // signUp.jsp
		//@애노테이션 써서 바로 해도된다.
	}
	  
	@PostMapping("/signUp")
	public String signUpSubmit(@ModelAttribute("signUp") Member member,
	                           @RequestParam String phone1,
	                           @RequestParam String phone2,
	                           @RequestParam String phone3,
	                           @RequestParam String pwConfirm,
	                           Model model) {
	    member.setPhone(phone1 + "-" + phone2 + "-" + phone3);
	    
	    if (!member.getPw().equals(pwConfirm)) {
	        model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
	        return "signUp";
	    }
	    
	    // 아이디 중복 검사
	    if (memberService.isDuplicateId(member.getMember_id())) {
	        model.addAttribute("error", "이미 존재하는 아이디입니다.");
	        return "signUp";
	    }
	    
	    // 닉네임 중복 확인
	    if (memberService.isDuplicateUsername(member.getUserName())) {
	        model.addAttribute("error", "이미 사용 중인 닉네임입니다.");
	        return "signUp";
	    }
	    
	    // Bean으로 등록한 암호화기 사용해서 비밀번호 암호화
	    member.setPw(passwordEncoder.encode(member.getPw()));
	    
	    memberService.save(member);
	    System.out.println("회원정보 저장 완료" + member);
	    
	    return "redirect:/login";
	  }
	
	@PostMapping("/register")
	public String register(@ModelAttribute Member member) {
		
		 System.out.println("전문가 인가 ? : " + member.isExpert());
		 
		 memberService.save(member);
		 return "redirect:/login";
	}

}
