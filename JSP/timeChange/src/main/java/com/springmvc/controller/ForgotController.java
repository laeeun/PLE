package com.springmvc.controller;

import java.time.format.DateTimeFormatter;

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
public class ForgotController {

    private final HomeController homeController;
	
	@Autowired
	MemberService memberService;

    ForgotController(HomeController homeController) {
        this.homeController = homeController;
    }
	
	@GetMapping("/findId")
	public String forgotIdForm() {
		return "findId";
	}
	
	@PostMapping("/findId")
	public String forgotId(@RequestParam String name, @RequestParam String phone, Model model) {
		System.out.println("아이디 찾기 들어옴");
		String findId = memberService.findId(name, phone);
		
		Member member = memberService.findIdWithCreatedAt(name, phone);
		
		if(member != null) {
			model.addAttribute("foundId", member.getMember_id());
		    model.addAttribute("joinDate", member.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
		    return "findResult";
		}else {
			model.addAttribute("error", "일치하는 ID가 없습니다.");
			return "findId";
		}
	}
	
	
	@GetMapping("/findPw")
	public String forgotPw() {
		return "findPw";
	}
	

    // 비밀번호 찾기 처리 (사용자 확인)
    @PostMapping("/findPw")
    public String forgitPw(@RequestParam String name,
                           @RequestParam String member_id,
                           @RequestParam String email,
                           Model model) {

        Member member = memberService.findByNameIdEmail(name, member_id, email);

        if (member == null) {
            model.addAttribute("error", "입력하신 정보와 일치하는 계정이 없습니다.");
            return "findPw";
        }

        model.addAttribute("member_id", member.getMember_id());
        System.out.println(member_id);
        return "resetPw";
    }
	
	@PostMapping("/resetPw")
	public String resetPw(@RequestParam String member_id, @RequestParam String newPw, @RequestParam String confirmPw, Model model) {
        if (!newPw.equals(confirmPw)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("member_id", member_id);
            return "resetPw";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPw = encoder.encode(newPw);
        memberService.updatePassword(member_id, encodedPw);

        return "redirect:/login";
	}
	
}
