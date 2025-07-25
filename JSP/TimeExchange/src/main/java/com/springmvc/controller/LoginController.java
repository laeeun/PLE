package com.springmvc.controller;

import java.time.LocalDateTime;

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
import com.springmvc.util.CaptchaUtil;

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
    public String loginProcess(@RequestParam String member_id,
                               @RequestParam String pw,
                               @RequestParam(required = false) String captchaInput,
                               HttpSession session, Model model) {

        // ✅ 아이디로 회원 조회
        Member member = memberService.login(member_id);

        if (member == null) {
            model.addAttribute("error", "존재하지 않는 회원입니다.");
            return "login";
        }

        // ✅ 로그인 시도 5회 이상이면 captcha 입력 요구
        if (member.getLoginFailCount() >= 5) {
            String captcha = (String) session.getAttribute("captcha");

            if (captcha == null || captchaInput == null || !captcha.equalsIgnoreCase(captchaInput)) {
                model.addAttribute("error", "보안문자가 틀렸습니다.");
                model.addAttribute("showCaptcha", true);
                return "login";
            }
        }

        // ✅ 로그인 차단 (6회 이상 실패 후 10분간 잠금)
        if (member.getLoginFailCount() >= 6) {
            LocalDateTime lastFailTime = member.getLastFailTime();
            if (lastFailTime != null && lastFailTime.isAfter(LocalDateTime.now().minusMinutes(10))) {
                model.addAttribute("error", "로그인 시도 6회 이상 실패. 10분 후 다시 시도해주세요.");
                return "login";
            } else {
                // 10분 경과 시 초기화
                member.setLoginFailCount(0);
                member.setLastFailTime(null);
                memberService.updateLoginFailInfo(member);
            }
        }

        // ❌ 비밀번호 불일치
        if (!passwordEncoder.matches(pw, member.getPw())) {
            member.setLoginFailCount(member.getLoginFailCount() + 1);
            member.setLastFailTime(LocalDateTime.now());
            memberService.updateLoginFailInfo(member);

            // DB에서 갱신된 값 확인 (로그용)
            Member refreshed = memberService.login(member.getMember_id());
            System.out.println("현재 실패 횟수: " + refreshed.getLoginFailCount());

            // 실패 5회 이상일 경우 캡차 생성
            if (member.getLoginFailCount() >= 5) {
                String generatedCaptcha = CaptchaUtil.generateCaptcha(5);
                session.setAttribute("captcha", generatedCaptcha);
                model.addAttribute("showCaptcha", true);
                model.addAttribute("captcha", generatedCaptcha);
            }

            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "login";
        }

        // ❌ 이메일 인증 미완료
        if (!member.isEmailVerified()) {
            model.addAttribute("error", "이메일 인증이 필요합니다.");
            model.addAttribute("member", member);
            return "emailNotVerified";
        }

        // ✅ 로그인 성공
        System.out.println("로그인 성공! 초기화 전 login_fail_count = " + member.getLoginFailCount());
        member.setLoginFailCount(0);
        member.setLastFailTime(null);
        memberService.updateLoginFailInfo(member);

        // 🔥 DB에서 다시 조회 후 세션에 저장
        Member refreshed = memberService.login(member.getMember_id());
        System.out.println("업데이트 후 DB 기준 login_fail_count = " + refreshed.getLoginFailCount());

        session.setAttribute("loggedInUser", refreshed);
        session.removeAttribute("captcha"); // 캡차 제거

        return "redirect:/";
    }

    
    // ✅ 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 모든 세션 제거
        return "home";
    }
}
