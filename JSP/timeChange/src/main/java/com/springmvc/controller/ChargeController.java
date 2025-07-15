package com.springmvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.Member;
import com.springmvc.service.purchaseService;

@Controller
@RequestMapping("/charge")
public class ChargeController {

    @Autowired
    private purchaseService purchaseService;

    /**
     * ⏳ 충전 페이지 진입
     */
    @GetMapping
    public String showChargeForm() {
        return "timeCharge";  // timeCharge.jsp
    }

    /**
     * ✅ 충전 실행 처리
     */
    @PostMapping
    public String processCharge(@RequestParam("amount") int amount,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        // 로그인 여부 확인
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        // 유효한 충전 금액인지 확인
        if (amount <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "1분 이상부터 충전 가능합니다.");
            return "redirect:/charge";
        }

        try {
            // 계정에 충전 수행 (음수 방지 로직은 Repository에 있음)
            purchaseService.updateAccountBalance(user.getMember_id(), amount);

            // 세션 정보 갱신
            user.setAccount(user.getAccount() + amount);
            session.setAttribute("loggedInUser", user);

            // 성공 메시지 전달
            redirectAttributes.addFlashAttribute("successMessage", amount + "분이 성공적으로 충전되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "충전 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "redirect:/charge";
    }
}
