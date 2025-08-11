package com.springmvc.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.Member;
import com.springmvc.domain.PasswordHistoryDTO;
import com.springmvc.service.MailService;
import com.springmvc.service.MemberService;
import com.springmvc.service.PasswordHistoryService;

@Controller
public class ForgotController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MailService mailService;

	@Autowired
	private PasswordHistoryService passwordHistoryService;

	@GetMapping("/findId")
	public String forgotIdForm() {
		return "findId";
	}

	@PostMapping("/findId")
	public String forgotId(@RequestParam String name, @RequestParam String phone, Model model) {
		System.out.println("아이디 찾기 들어옴");
		Member member = memberService.findIdWithCreatedAt(name, phone);

		if (member != null) {
			model.addAttribute("foundId", member.getMember_id());
			model.addAttribute("joinDate", member.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
			return "findResult";
		} else {
			model.addAttribute("error", "일치하는 ID가 없습니다.");
			return "findId";
		}
	}

	@GetMapping("/findPw")
	public String forgotPw() {
		return "findPw";
	}

	// 비밀번호 찾기 처리 (사용자 확인)
	@PostMapping("/findPw")
	public String forgotPw(@RequestParam String name, @RequestParam String member_id, @RequestParam String email,
			Model model, RedirectAttributes redirectAttributes) {

		Member member = memberService.findByNameIdEmail(name, member_id, email);

		if (member == null) {
			model.addAttribute("error", "입력하신 정보와 일치하는 계정이 없습니다.");
			return "findPw";
		}

		// ✅ 임시 비밀번호 생성 (현재시각 + 랜덤 숫자)
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
		int random = (int) (Math.random() * 90 + 10);
		String tempPassword = timestamp + random;

		// ✅ 비밀번호 암호화
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPw = encoder.encode(tempPassword);

		// ✅ 암호화된 비번 + 원문 임시비번 저장
		memberService.updatePasswordWithTemp(member_id, encodedPw, tempPassword);

		// ✅ 이메일 발송
		mailService.sendTemporaryPasswordMail(member.getEmail(), tempPassword);

		// ✅ 성공 메시지
		redirectAttributes.addFlashAttribute("pwResetSuccess", true);
		return "redirect:/login";
	}

	@PostMapping("/resetPw")
	public String resetPw(@RequestParam String member_id, @RequestParam String currentPw, @RequestParam String newPw,
			@RequestParam String confirmPw, RedirectAttributes redirectAttributes, Model model, HttpSession session) {

		Member member = memberService.findById(member_id);
		if (member == null) {
			model.addAttribute("error", "해당 회원이 존재하지 않습니다.");
			return "resetPw";
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		// ✅ 현재 비밀번호 일치 여부 확인
		if (!encoder.matches(currentPw, member.getPw())) {
			model.addAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
			model.addAttribute("member_id", member_id);
			return "resetPw";
		}

		// ✅ 새 비밀번호 두 개 확인
		if (!newPw.equals(confirmPw)) {
			model.addAttribute("error", "새 비밀번호가 일치하지 않습니다.");
			model.addAttribute("member_id", member_id);
			return "resetPw";
		}

		// 최근 3개 비밀번호와 중복 체크
		List<PasswordHistoryDTO> historyList = passwordHistoryService.findRecent3ByMemberId(member_id);
		for (PasswordHistoryDTO history : historyList) {
			if (encoder.matches(newPw, history.getEncodedPw())) {
				model.addAttribute("error", "최근에 사용한 비밀번호는 사용할 수 없습니다.");
				model.addAttribute("member_id", member_id);
				return "resetPw";
			}
		}

		// ✅ 암호화 후 변경
		String encodedPw = encoder.encode(newPw);
		memberService.updatePassword(member_id, encodedPw);

		redirectAttributes.addFlashAttribute("pwResetSuccess", true);
		Member updated = memberService.findById(member_id);
		System.out.println("업데이트된 비밀번호" + updated.getPw());
		session.setAttribute("loggedInUser", updated);
		return "redirect:/mypage";
	}

}
