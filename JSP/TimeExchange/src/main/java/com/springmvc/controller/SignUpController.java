package com.springmvc.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.ExpertProfileDTO;
import com.springmvc.domain.Member;
import com.springmvc.domain.MemberStatus;
import com.springmvc.service.ExpertProfileService;
import com.springmvc.service.MailService;
import com.springmvc.service.MemberService;


@Controller
public class SignUpController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;
    
    @Autowired
    private ExpertProfileService expertProfileService;

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
            	System.out.println("📷 프로필 이미지 객체: " + member.getProfileImageFile());
            	System.out.println("📷 파일명: " + (member.getProfileImageFile() != null ? member.getProfileImageFile().getOriginalFilename() : "null"));

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
            member.setProfileImage("default-profile.png");
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

        // ✅ 전문가 여부에 따라 다음 단계 분기
        if (member.getExpert()) {
            // expertForm에서 사용할 memberId를 세션에 저장
            session.setAttribute("expertMemberId", member.getMember_id());
            return "redirect:/expertForm";  // 전문가 추가 정보 입력 폼으로 이동
        }

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
        System.out.println("전문가 인가 ? : " + member.getExpert());
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
    
    @GetMapping("/expertForm")
    public String expertForm(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("expertMemberId");

        if (memberId == null) {
            // 비정상 접근: 세션에 memberId 없을 경우 홈으로 돌려보내기
            return "redirect:/";
        }

        model.addAttribute("memberId", memberId);
        return "expertForm";  // => /WEB-INF/views/expertForm.jsp
    }

    @PostMapping("/expertSubmit")
    public String expertSubmit(@RequestParam("memberId") String memberId,
                               @RequestParam("career") String career,
                               @RequestParam("university") String university,
                               @RequestParam("certification") String certification,
                               @RequestParam("introduction") String introduction,
                               @RequestParam("expertFiles") List<MultipartFile> files,
                               RedirectAttributes redirectAttributes) {

    	
        System.out.println("📥 전문가 정보 제출: " + memberId);
        System.out.println("📌 경력: " + career);
        System.out.println("📌 대학교: " + university);
        System.out.println("📌 자격증: " + certification);

        List<String> savedFileNames = new ArrayList<>();

        // ✅ 파일 저장 처리
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        String originalFilename = file.getOriginalFilename();
                        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                        String savedName = timestamp + "_" + originalFilename;

                        String uploadDir = "c:/upload/expert/";
                        File dir = new File(uploadDir);
                        if (!dir.exists()) dir.mkdirs();

                        File dest = new File(uploadDir + savedName);
                        file.transferTo(dest);

                        savedFileNames.add(savedName);
                    } catch (Exception e) {
                        e.printStackTrace();
                        redirectAttributes.addFlashAttribute("error", "파일 업로드 중 오류가 발생했습니다.");
                        return "redirect:/expertForm";
                    }
                }
            }
        }

        // ✅ DTO 생성 후 저장
        ExpertProfileDTO dto = new ExpertProfileDTO(
            memberId,
            career,
            university,
            certification,
            introduction,
            savedFileNames
        );
        expertProfileService.save(dto);

        redirectAttributes.addFlashAttribute("success", "전문가 정보가 저장되었습니다!");
        return "successSignUp";
    }


}
