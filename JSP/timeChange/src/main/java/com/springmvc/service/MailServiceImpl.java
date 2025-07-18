package com.springmvc.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.springmvc.domain.Member;

@Service  // 💡 Spring이 Service로 인식하도록
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationMail(Member member) {
        String token = UUID.randomUUID().toString();
        member.setEmailToken(token);
        member.setTokenCreatedAt(LocalDateTime.now());

        String link = "http://localhost:8080/member/verify?token=" + token;

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(member.getEmail());
            helper.setSubject("[TimeFair] 이메일 인증 안내");

            // ✅ HTML 형식의 메일 본문 작성
            String html = """
                <div style="font-family: Pretendard, sans-serif; line-height: 1.7;">
                    <h2>[TimeFair] 이메일 인증 안내</h2>
                    <p>안녕하세요.</p>
                    <p>아래 버튼을 클릭해서 이메일 인증을 완료해주세요.</p>
                    <p>
                        <a href='%s' style='padding: 10px 20px; background: linear-gradient(to right, #a855f7, #ec4899); 
                        color: white; border-radius: 8px; text-decoration: none;'>인증 완료</a>
                    </p>
                    <p style='margin-top: 20px;'>또는 아래 링크를 브라우저에 복사해서 여세요:<br>%s</p>
                </div>
            """.formatted(link, link);

            helper.setText(html, true);  // 두 번째 true = HTML 메일

            helper.setFrom("your_email@gmail.com"); // Gmail 발신 주소

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // 나중에 예외 처리 추가해도 좋아
        }
    }

}
