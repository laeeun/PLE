package com.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.HistoryDTO;
import com.springmvc.domain.Member;
import com.springmvc.domain.PasswordHistoryDTO;
import com.springmvc.service.ExpertProfileService;
import com.springmvc.service.HistoryService;
import com.springmvc.service.MemberService;
import com.springmvc.service.PasswordHistoryService;
import com.springmvc.service.ReviewService;

@Controller
@RequestMapping("/mypage")
public class MyPageController  {
	@Autowired
	private MemberService memberService;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private PasswordHistoryService passwordHistoryService;
    
    @Autowired
    private ExpertProfileService expertProfileService;
	
	@GetMapping
    public String myPage(HttpSession session, Model model) {
		Member sessionMember = (Member) session.getAttribute("loggedInUser");
	    if (sessionMember == null) {
	        return "redirect:/login";
	    }

	    String loginId = sessionMember.getMember_id();
	    Member member = memberService.findById(loginId);
	    model.addAttribute("member", member);

	    // ✅ 전문가 프로필 추가
	    expertProfileService.findByMemberId(loginId)
	        .ifPresent(profile -> model.addAttribute("expertProfile", profile));

	    return "mypage";
    }
	
	
	@GetMapping("/edit")
	public String editForm(HttpSession session, Model model) {
	    Member login = (Member) session.getAttribute("loggedInUser");
	    if (login == null) {
	        return "redirect:/login"; // 로그인 안했으면 로그인 페이지로
	    }

	    Member member = memberService.findById(login.getMember_id());

	    // 이메일 분리
	    if (member.getEmail() != null && member.getEmail().contains("@")) {
	        String[] emailParts = member.getEmail().split("@");
	        member.setEmailId(emailParts[0]);
	        member.setEmailDomain(emailParts[1]);
	    }

	    // 전화번호 분리
	    if (member.getPhone() != null && member.getPhone().contains("-")) {
	        String[] phoneParts = member.getPhone().split("-");
	        if (phoneParts.length == 3) {
	            member.setPhone1(phoneParts[0]);
	            member.setPhone2(phoneParts[1]);
	            member.setPhone3(phoneParts[2]);
	        }
	    }
	    System.out.println(member);
	    model.addAttribute("member", member);

	    return "editForm";
	}

	
	@PostMapping("/edit")
	   public String edit(@ModelAttribute Member member, HttpServletRequest request, RedirectAttributes redirectAttributes,HttpSession session) {
	      Member login = (Member) session.getAttribute("loggedInUser");
	          if (login == null) {
	              return "redirect:/login"; // 로그인 안 된 경우
	             }
	       // 이메일 조합
	       String emailId = request.getParameter("emailId");
	       String emailDomain = request.getParameter("emailDomain");
	       if (emailId != null && emailDomain != null) {
	           member.setEmail(emailId + "@" + emailDomain);
	           member.setEmailId(emailId);
	           member.setEmailDomain(emailDomain);
	       }
	       
	       // 전화번호 조합
	       String phone1 = request.getParameter("phone1");
	       String phone2 = request.getParameter("phone2");
	       String phone3 = request.getParameter("phone3");
	       if (phone1 != null && phone2 != null && phone3 != null) {
	           member.setPhone1(phone1);
	           member.setPhone2(phone2);
	           member.setPhone3(phone3);
	           member.setPhone(phone1 + "-" + phone2 + "-" + phone3); // 필요시
	       }
	       member.setEmailVerified(login.isEmailVerified());
	       member.setRole(login.getRole());
	       member.setAccount(login.getAccount());
	       member.setCreatedAt(login.getCreatedAt());
	       member.setProfileImage(login.getProfileImage());
	       // 회원 정보 업데이트 처리
	       memberService.update(member);
	       session.setAttribute("loggedInUser", member);
	       redirectAttributes.addFlashAttribute("success", "회원 정보가 수정되었습니다.");

	       return "redirect:/mypage";
	   }


	
	@PostMapping("/delete")
	public String delete(HttpSession session) {
		Member loggedInUser = (Member) session.getAttribute("loggedInUser");
		String member_id = loggedInUser.getMember_id();
		memberService.delete(member_id);
		session.invalidate();
		return "redirect:/";
	}
	
	

	
	@GetMapping("/changePw")
	public String changePw() {
		    return "changePasswordForm";
	}
	
	@PostMapping("/changePw")
	public String changePassword(@RequestParam String currentPw,
	                             @RequestParam String newPw,
	                             @RequestParam String pwConfirm,
	                             HttpSession session,
	                             RedirectAttributes redirectAttributes,
	                             Model model) {

	    Member loggedInUser = (Member) session.getAttribute("loggedInUser");
	    if (loggedInUser == null) {
	        return "redirect:/login";
	    }

	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	    // 1. 현재 비밀번호 일치 여부 확인
	    if (!encoder.matches(currentPw, loggedInUser.getPw())) {
	        redirectAttributes.addFlashAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
	        return "redirect:/mypage/changePw";
	    }

	    // 2. 새 비밀번호 두 개 일치 여부 확인
	    if (!newPw.equals(pwConfirm)) {
	        redirectAttributes.addFlashAttribute("error", "새 비밀번호가 일치하지 않습니다.");
	        return "redirect:/mypage/changePw";
	    }

	    // 3. 최근 3개 비밀번호 중복 체크
	    List<PasswordHistoryDTO> historyList = passwordHistoryService.findRecent3ByMemberId(loggedInUser.getMember_id());
	    for (PasswordHistoryDTO history : historyList) {
	        if (encoder.matches(newPw, history.getPassword_hash())) {
	        	 redirectAttributes.addFlashAttribute("error", "최근에 사용한 비밀번호는 사용할 수 없습니다.");
	            return "redirect:/mypage/changePw";
	        }
	    }

	    // 4. 암호화 후 비밀번호 변경
	    String encodedPw = encoder.encode(newPw);
	    memberService.updatePassword(loggedInUser.getMember_id(), encodedPw);

	    // 5. 세션 정보 최신화
	    Member updated = memberService.findById(loggedInUser.getMember_id());
	    session.setAttribute("loggedInUser", updated);

	    redirectAttributes.addFlashAttribute("success", "비밀번호가 성공적으로 변경되었습니다!");
	    return "redirect:/mypage";
	}

	
	@GetMapping("/history")
	public String history(HttpSession session, Model model) {
	    Member loggedInUser = (Member) session.getAttribute("loggedInUser");
	    if (loggedInUser == null) {
	        return "redirect:/login";
	    }

	    String member_id = loggedInUser.getMember_id();

	    List<HistoryDTO> historyList = historyService.findByMemberId(member_id);

	    for (HistoryDTO dto : historyList) {
	        // 구매자인 경우에만 리뷰 작성 여부 확인
	        if (dto.getBuyer_id().equals(member_id)) {
	            boolean exists = reviewService.existsByHistoryId(dto.getHistory_id());
	            dto.setReview_written(exists);

	            if (exists) {
	                Long reviewId = reviewService.findIdByHistoryId(dto.getHistory_id());
	                dto.setReview_id(reviewId);
	            } else {
	                dto.setReview_id(null);
	            }
	        } else {
	            dto.setReview_written(true); // 판매자는 항상 "받은 리뷰 보기"로 간주
	        }
	    }

	    model.addAttribute("historyList", historyList);
	    model.addAttribute("loggedInUser", loggedInUser);

	    return "History";
	}


	
	
}
