package com.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.domain.Member;
import com.springmvc.service.MailService;
import com.springmvc.service.MemberService;

@RequestMapping("/mail")
@Controller
public class MailController {
    @Autowired
    private MailService mailService;

    @Autowired
    private MemberService memberService;

    // ✅ 이메일 인증 다시 보내기 (재인증 요청용)
    @PostMapping("/resend")
    public String resendVerificationEmail(@RequestParam("member_id") String memberId, Model model) {
    	
    	System.out.println("입력받은 member_id = " + memberId);
    	System.out.println("찾은 회원 정보 = " + memberId);

        Member member = memberService.findById(memberId);

        if (member == null) {
            model.addAttribute("error", "회원 정보를 찾을 수 없습니다.");
            return "verifyResult"; // 또는 에러 페이지
        }

        if (member.isEmailVerified()) {
            model.addAttribute("message", "이미 이메일 인증이 완료된 계정입니다.");
            return "verifyResult";
        }
        
        if (!member.isTokenExpired(10)) { // 10분 안 지났으면
            model.addAttribute("message", "인증 메일은 10분 후에 다시 보낼 수 있습니다.");
            return "verifyResult";
        }

        mailService.sendVerificationMail(member);
        model.addAttribute("message", "이메일 인증 메일을 다시 보냈습니다. 메일함을 확인해주세요!");
        return "verifyResult";
    }

    // ✅ 테스트 메일 보내기 (관리자용)
    @GetMapping("/test")
    @ResponseBody
    public String testMail(@RequestParam("to") String email) {
        Member dummy = new Member();
        dummy.setName("테스트유저");
        dummy.setEmail(email);
        mailService.sendVerificationMail(dummy);
        return "테스트 메일 발송 완료: " + email;
    }
}
