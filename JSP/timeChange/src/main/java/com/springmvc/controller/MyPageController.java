package com.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import com.springmvc.service.HistoryService;
import com.springmvc.service.MemberService;
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
	
	@GetMapping
    public String myPage(HttpSession session, Model model) {
		Member sessionMember = (Member) session.getAttribute("loggedInUser");
        if (sessionMember == null) {
            return "redirect:/login";
        }

        String loginId = sessionMember.getMember_id();
        Member member = memberService.findById(loginId);
        model.addAttribute("member", member);
        return "mypage";
    }
	
	
	@GetMapping("/edit")
	public String editForm(@RequestParam("id") String member_id, Model model) {
		
		Member member = memberService.findById(member_id);
		
		model.addAttribute(member);
		
		return "editForm";
	}
	
	@PostMapping("/edit")
	public String edit(@ModelAttribute Member member, 
	                   HttpServletRequest request,
	                   RedirectAttributes redirectAttributes) {

	    // ✅ 이메일 조합을 먼저!
	    String emailId = request.getParameter("emailId");
	    String emailDomain = request.getParameter("emailDomain");
	    if (emailId != null && emailDomain != null) {
	        member.setEmail(emailId + "@" + emailDomain);
	    }

	    // ✅ 회원 정보 업데이트
	    memberService.update(member);

	    // ✅ 성공 메시지
	    redirectAttributes.addFlashAttribute("success", "회원 정보가 수정되었습니다.");

	    System.out.println("회원정보 업데이트 완료 !!");

	    return "successSignUp";
	}

	
	@PostMapping("/delete")
	public String delete(HttpSession session) {
		Member loggedInUser = (Member) session.getAttribute("loggedInUser");
		String member_id = loggedInUser.getMember_id();
		memberService.delete(member_id);
		session.invalidate();
		return "redirect:/";
	}
	
	
	@GetMapping("/verifyPw")
	public String verifyPasswordForm() {
		return "verifyPasswordForm";
	}
	
	@PostMapping("/verifyPw")
	public String verifyPassword(@RequestParam("currentPw") String currentPw,
								 HttpSession session, RedirectAttributes redirectAttributes) {
		 System.out.println(">> 현재 비밀번호 입력됨: " + currentPw);
		 Member loggedInUser = (Member) session.getAttribute("loggedInUser");
		 
		  if (loggedInUser == null) {
	            return "redirect:/login";
	        }

	        // 비밀번호 일치 여부 확인
	        if (!passwordEncoder.matches(currentPw, loggedInUser.getPw())) {
	            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
	            return "redirect:/mypage/verifyPw";
	        }

	        // ✅ 비밀번호 인증 성공 → 다음 화면으로 이동
	        System.out.println("비밀번호 변경창으로이동 !!");
	        return "redirect:/mypage/changePw";
	    }
	
	@GetMapping("/changePw")
	public String changePw() {
		    return "changePasswordForm";
	}
	
	@PostMapping("/changePw")
	public String changePassword(@RequestParam String newPw,
	                             @RequestParam String pwConfirm,
	                             HttpSession session,
	                             RedirectAttributes redirectAttributes) {
		
	    Member loggedInUser = (Member) session.getAttribute("loggedInUser");

	    if (loggedInUser == null) {
	        return "redirect:/login";
	    }

	    if (!newPw.equals(pwConfirm)) {
	        redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
	        return "redirect:/mypage/changePw";
	    }

	    String encodedPw = passwordEncoder.encode(newPw);
	    memberService.updatePassword(loggedInUser.getMember_id(), encodedPw);
	    redirectAttributes.addFlashAttribute("success", "비밀번호가 성공적으로 변경되었습니다!");
	    System.out.println(">> 비밀번호 변경완료 ");
	    return "redirect:/";
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
	        if (dto.getBuyer_id().equals(member_id)) {
	            try {
	                Long reviewId = reviewService.findIdByBuyerAndTalent(member_id, dto.getTalent_id());
	                dto.setReview_written(true);
	                dto.setReview_id(reviewId);
	            } catch (EmptyResultDataAccessException e) {
	                dto.setReview_written(false);
	                dto.setReview_id(null);
	            }
	        } else {
	            dto.setReview_written(true);
	        }
	    }

	    // JSP로 전달
	    model.addAttribute("historyList", historyList);
	    model.addAttribute("loggedInUser", loggedInUser);

	    return "History"; // 📄 /WEB-INF/views/History.jsp
	}


	
}
