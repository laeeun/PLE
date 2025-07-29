package com.springmvc.service;

public interface SmsService {
	
    void sendCertCode(String phone); // 인증번호 전송
    
    boolean verifyCode(String phone, String code); // 인증번호 검증
}
