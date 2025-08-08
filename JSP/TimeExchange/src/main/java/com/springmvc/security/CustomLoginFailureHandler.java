package com.springmvc.security;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage = "로그인에 실패하였습니다.";

        // ✅ CustomAuthenticationProvider에서 발생시킨 메시지 활용
        if (exception instanceof BadCredentialsException) {
            errorMessage = exception.getMessage(); // ← 여기서 커스텀 메시지 그대로 가져옴
        }

        // ✅ 메시지를 URL 인코딩해서 쿼리 파라미터로 전달
        response.sendRedirect(request.getContextPath() + "/login?error=true&message=" + URLEncoder.encode(errorMessage, "UTF-8"));
    }
}
