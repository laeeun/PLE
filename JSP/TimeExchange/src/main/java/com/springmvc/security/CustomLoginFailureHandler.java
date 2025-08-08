package com.springmvc.security;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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

	    // 🔹 탈퇴 회원이면 복구 페이지로 아이디 넘겨서 리다이렉트
	    if (exception instanceof InactiveMemberException) {
	        String memberId = ((InactiveMemberException) exception).getMemberId();
	        String url = request.getContextPath() + "/deactivated?u=" +
	                URLEncoder.encode(memberId, "UTF-8");
	        response.sendRedirect(url);
	        return;
	    }

	    // 🔹 그 외 로그인 실패
	    String errorMessage = "로그인에 실패하였습니다.";
	    if (exception instanceof BadCredentialsException || exception instanceof DisabledException) {
	        errorMessage = exception.getMessage();
	    }
	    String redirectUrl = "/login?error=true&message=" +
	            URLEncoder.encode(errorMessage, "UTF-8");

	    response.sendRedirect(request.getContextPath() + redirectUrl);
	}
}
