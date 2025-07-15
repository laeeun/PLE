package com.springmvc.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
                                        throws IOException, ServletException {
        String memberId = request.getParameter("member_id");
        String pw = request.getParameter("pw");

        System.out.println("❌ 로그인 실패!");
        System.out.println("❌ 입력된 ID: " + memberId);
        System.out.println("❌ 입력된 PW: " + pw); // 실제 비번 (주의!)
        System.out.println("❌ 실패 이유: " + exception.getMessage());

        response.sendRedirect("/login?error=true");
    }
}

