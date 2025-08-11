package com.springmvc.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import com.springmvc.domain.Member;

@Service  // 💡 Spring이 Service로 인식하도록
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private MemberService memberService;

    @Override
    @Async
    public void sendVerificationMail(Member member) {
        String token = UUID.randomUUID().toString();
        member.setEmailToken(token);
        member.setTokenCreatedAt(LocalDateTime.now());

        memberService.update(member);

        String link = "http://localhost:8080/TimeExchange/verify?token=" + token;

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(member.getEmail());
            helper.setSubject("[TimeFair] 이메일 인증 안내");

            // ✅ 테마만 변경 (흰 배경 + 주황 포인트)
            String html = """
                <div style="font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
                            line-height: 1.6; color: #1F2C40; background: #F9F9F9; padding: 30px; border-radius: 16px; border: 1px solid #E8E8E8;">
                    <h2 style="color: #1F2C40; margin-bottom: 12px;">[TimeFair] 이메일 인증 안내</h2>
                    <p style="font-size: 15px; color:#6A737D; margin-bottom: 20px;">안녕하세요,</p>
                    <p style="font-size: 15px; margin-bottom: 28px;">
                        아래 버튼을 클릭해서 이메일 인증을 완료해주세요.
                    </p>
                    <p style="text-align: center; margin-bottom: 28px;">
                        <a href='%s' 
                           style="
                             display: inline-block;
                             padding: 14px 28px;
                             background: linear-gradient(90deg, #FF6B35, #FF8A5B);
                             color: #FFFFFF !important;
                             font-weight: 700;
                             font-size: 16px;
                             border-radius: 14px;
                             text-decoration: none;
                             box-shadow: 0 6px 18px rgba(255, 107, 53, 0.25);
                             transition: background 0.3s ease;
                           "
                           onmouseover="this.style.background='linear-gradient(90deg, #FF8A5B, #FF6B35)'" 
                           onmouseout="this.style.background='linear-gradient(90deg, #FF6B35, #FF8A5B)'">
                           인증 완료
                        </a>
                    </p>
                    <p style="font-size: 13px; color: #6A737D;">
                        또는 아래 링크를 브라우저에 복사해서 여세요:<br>
                        <a href="%s" style="color: #FF6B35;">%s</a>
                    </p>
                    <hr style="margin: 32px 0; border: none; border-top: 1px solid #E8E8E8;">
                    <p style="margin-top: 8px; font-size: 12px; color: #6A737D;">
                        본 메일은 발신 전용입니다. 문의 사항이 있으시면 고객센터로 연락해 주세요.
                    </p>
                </div>
                """.formatted(link, link, link);

            helper.setText(html, true);
            helper.setFrom("moteyv123@gmail.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    @Async
    public void sendTemporaryPasswordMail(String to, String tempPassword) {
        String subject = "[TimeFair] 임시 비밀번호 안내";

        // ✅ 테마만 변경 (흰 배경 + 주황 포인트)
        String html = """
            <div style="font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
                        line-height: 1.6; color: #1F2C40; background: #F9F9F9; padding: 30px; border-radius: 16px; border: 1px solid #E8E8E8;">
                <h2 style="color: #1F2C40; margin-bottom: 12px;">[TimeFair] 임시 비밀번호 안내</h2>
                <p style="font-size: 15px; color:#6A737D; margin-bottom: 18px;">안녕하세요!</p>
                <p style="font-size: 15px; margin-bottom: 24px;">
                    요청하신 임시 비밀번호는 아래와 같습니다.
                </p>
                <p style="
                    font-weight: 700;
                    font-size: 20px;
                    color: #FF6B35;
                    background: #FFEEEA;
                    padding: 12px 24px;
                    border-radius: 12px;
                    text-align: center;
                    width: fit-content;
                    margin: 0 auto 26px auto;
                    box-shadow: 0 4px 12px rgba(255, 107, 53, 0.25);
                ">
                    %s
                </p>
                <p style="font-size: 13px; color:#6A737D;">
                    해당 비밀번호로 로그인하신 후 반드시 새 비밀번호로 변경해 주세요.
                </p>
                <hr style="margin: 32px 0; border: none; border-top: 1px solid #E8E8E8;">
                <p style="margin-top: 8px; font-size: 12px; color: #6A737D;">
                    본 메일은 발신 전용입니다. 문의 사항이 있으시면 고객센터로 연락해 주세요.
                </p>
            </div>
            """.formatted(tempPassword);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom("moteyv123@gmail.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
