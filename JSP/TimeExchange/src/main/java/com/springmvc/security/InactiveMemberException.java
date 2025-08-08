package com.springmvc.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 탈퇴한 회원일 때 던지는 예외
 * memberId를 같이 보관해서 FailureHandler에서 활용 가능
 */
public class InactiveMemberException extends AuthenticationException {

    private final String memberId;

    public InactiveMemberException(String memberId) {
        super("탈퇴한 회원입니다.");
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }
}