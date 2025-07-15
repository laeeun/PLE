package com.springmvc.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.springmvc.domain.Member;
import com.springmvc.service.MemberService;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;

    public LoginSuccessHandler(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String member_id = authentication.getName();
        System.out.println("✅ 로그인 성공: " + member_id); // 여기에 로그 찍기
        
        Member member = memberService.findById(member_id);
        System.out.println("✅ 사용자 객체: " + member); // null인지 확인

        // ✅ 세션에 사용자 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", member);
        System.out.println("✅ 세션에 저장 완료");

        // ✅ 로그인 후 홈으로 이동
        response.sendRedirect(request.getContextPath() + "/");
    }
}
