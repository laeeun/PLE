package com.springmvc.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.springmvc.domain.Member;


public interface MailService {

    void sendVerificationMail(Member member);
    
    void sendTemporaryPasswordMail(String to, String tempPassword);
   
}
