package com.springmvc.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.Member;
import com.springmvc.service.MailService;
import com.springmvc.service.MemberService;
import com.springmvc.domain.MemberStatus;

@RequestMapping("/signUp")
@Controller
public class SignUpController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("signUp", new Member());
        System.out.println("회원가입 페이지로 이동");
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUpSubmit(@ModelAttribute("signUp") Member member,
                               Model model, HttpSession session,
                               RedirectAttributes redirectAttributes) {

        System.out.println("📥 [DEBUG] 요청받은 member 객체확인" + member);
        System.out.println("📥 [DEBUG] 회원가입 요청 도착");

        // 📌 전화번호 조합
        if (member.getPhone1() != null && member.getPhone2() != null && member.getPhone3() != null) {
            member.setPhone(member.getPhone1() + "-" + member.getPhone2() + "-" + member.getPhone3());
        }

        // 📌 이메일 조합
        if (member.getEmailId() != null && member.getEmailDomain() != null) {
            member.setEmail(member.getEmailId() + "@" + member.getEmailDomain());
        }

        // ❌ 비밀번호 확인
        String pw = member.getPw();
        String pwConfirm = member.getPwConfirm();
        if (pw == null || pwConfirm == null || !pw.equals(pwConfirm)) {
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "signUp";
        }
        
        // ✅ 아이디 입력 여부 확인
        if (member.getMember_id() == null || member.getMember_id().trim().isEmpty()) {
            model.addAttribute("errorMessage", "아이디를 입력해주세요.");
            return "signUp";
        }


        // ❌ 아이디 중복 확인
        if (memberService.isDuplicateId(member.getMember_id())) {
            model.addAttribute("errorMessage", "이미 사용중인 아이디입니다.");
            return "signUp";
        }

        // ❌ 닉네임 중복 확인
        if (memberService.isDuplicateUsername(member.getUserName())) {
            model.addAttribute("errorMessage", "이미 사용중인 닉네임입니다.");
            return "signUp";
        }

        // ❌ 이메일 중복 확인
        if (memberService.existsByEmail(member.getEmail())) {
            model.addAttribute("errorMessage", "이미 사용중인 이메일입니다. 다른 이메일을 사용하세요.");
            return "signUp";
        }

        // 프로필 이미지 처리
        MultipartFile file = member.getProfileImageFile();
        if (file != null && !file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
                String savedFilename = timestamp + "_" + originalFilename;

                String uploadDir = "c:/upload/profile/";
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                File dest = new File(uploadDir + savedFilename);
                file.transferTo(dest);
                member.setProfileImage(savedFilename);

            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "프로필 이미지 업로드 중 오류가 발생했습니다.");
                return "signUp";
            }
        } else {
            member.setProfileImage("default_profile.png");
        }

        // ✅ 비밀번호 암호화
        member.setPw(passwordEncoder.encode(member.getPw()));

        // ✅ 회원 상태 설정
        member.setStatus(MemberStatus.ACTIVE);

        // ✅ 회원 정보 저장
        memberService.save(member);
        System.out.println("✔ 회원 저장 완료. 메일 발송 시작");

        // ✅ 이메일 인증 메일 발송
        mailService.sendVerificationMail(member);
        System.out.println("✔ 메일 발송 완료");

        return "successSignUp";
    }

    @GetMapping("/verify")
    public String verify(@RequestParam("token") String token, Model model) {
        Member member = memberService.findByEmailToken(token);

        if (member == null) {
            model.addAttribute("error", "잘못된 인증 링크입니다.");
            return "verifyResult";
        }

        member.setEmailVerified(true);
        member.setEmailToken(null);
        memberService.update(member);

        model.addAttribute("message", "이메일 인증이 완료되었습니다!");
        return "verifyResult";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Member member) {
        System.out.println("전문가 인가 ? : " + member.isExpert());
        memberService.save(member);
        return "redirect:/login";
    }


    // ✅ 아이디 중복 확인 (AJAX)
    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam("member_id") String member_id) {
        System.out.println("🔍 컨트롤러 도착: member_id = " + member_id);
        boolean exists = memberService.isDuplicateId(member_id);
        return exists ? "duplicated" : "ok";
    }

    // ✅ 닉네임 중복 확인 (AJAX)
    @GetMapping("/checkUsername")
    @ResponseBody
    public String checkUsername(@RequestParam("userName") String username) {
        boolean exists = memberService.isDuplicateUsername(username);
        return exists ? "duplicated" : "available";
    }

    // ✅ 이메일 중복 확인 (AJAX)
    @GetMapping("/checkEmail")
    @ResponseBody
    public String checkEmail(@RequestParam("email") String email) {
        boolean exists = memberService.existsByEmail(email);
        return exists ? "duplicated" : "available";
    }

    /*
    @PostMapping("/sendCode")
    @ResponseBody
    public String sendCertCode(@RequestParam String phone) {
        try {
            smsService.sendCertCode(phone);
            return "인증번호가 전송되었습니다.";
        } catch (Exception e) {
            e.printStackTrace();
            return "인증번호 전송에 실패했습니다.";
        }
    }

    @PostMapping("/verifyCode")
    @ResponseBody
    public String verifyCode(@RequestParam String phone, @RequestParam String inputCode, HttpSession session) {
        boolean result = smsService.verifyCode(phone, inputCode);
        if (result) {
            session.setAttribute("phoneVerified:" + phone, true);
            return "인증 성공!";
        } else {
            return "인증 실패! 인증번호가 틀렸거나 만료되었습니다.";
        }
    }
    */
}
