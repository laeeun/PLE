package com.springmvc.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.springmvc.domain.Member;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Member member = (Member) authentication.getPrincipal();  // CustomAuthenticationProvider에서 설정한 Member
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", member);  // ✅ 세션에 저장

        response.sendRedirect(request.getContextPath() + "/");
    }
}
