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
import com.springmvc.service.MailService;
import com.springmvc.service.MemberService;


@Controller
public class SignUpController {
 
	@Autowired	
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private MailService mailService;

	
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
	                           @RequestParam String emailId,          // 📌 추가
	                           @RequestParam String emailDomain,      // 📌 추가
	                           Model model) {

	    // 📌 전화번호 조합
	    member.setPhone(phone1 + "-" + phone2 + "-" + phone3);

	    // 📌 이메일 조합
	    member.setEmail(emailId + "@" + emailDomain);

	    // ❌ 비밀번호 확인
	    if (!member.getPw().equals(pwConfirm)) {
	        model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
	        return "signUp";
	    }

	    // ❌ 아이디 중복 확인
	    if (memberService.isDuplicateId(member.getMember_id())) {
	        model.addAttribute("error", "이미 존재하는 아이디입니다.");
	        return "signUp";
	    }

	    // ❌ 닉네임 중복 확인
	    if (memberService.isDuplicateUsername(member.getUserName())) {
	        model.addAttribute("error", "이미 사용 중인 닉네임입니다.");
	        return "signUp";
	    }
	    
	    System.out.println("비번암호화");

	    // ✅ 비밀번호 암호화
	    member.setPw(passwordEncoder.encode(member.getPw()));

	    // ✅ 저장
	    memberService.save(member);
	    System.out.println("회원정보 저장 완료: " + member);
	    
	    System.out.println("✔ 회원 저장 완료. 메일 발송 시작");

        // ✅ 이메일 인증 메일 발송
        mailService.sendVerificationMail(member);

        System.out.println("✔ 메일 발송 완료");
        
        // ✅ 인증 안내 화면으로 이동
        return "successSignUp";  
	}

	
	@PostMapping("/register")
	public String register(@ModelAttribute Member member) {
		
		 System.out.println("전문가 인가 ? : " + member.isExpert());
		 
		 memberService.save(member);
		 return "redirect:/login";
	}

}
