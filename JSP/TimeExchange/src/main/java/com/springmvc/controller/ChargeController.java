package com.springmvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.Member;
import com.springmvc.service.purchaseService;

@Controller // 이 클래스가 컨트롤러 역할을 한다고 스프링에게 알려주는 어노테이션
@RequestMapping("/charge") // 이 컨트롤러의 모든 메서드는 "/charge"로 시작하는 URL을 처리한다
public class ChargeController {

    @Autowired // 의존성 자동 주입 - purchaseService 객체를 자동으로 주입받음
    private purchaseService purchaseService;

    /**
     * ⏳ 충전 페이지 진입 (GET 요청)
     * 사용자가 /charge로 접속했을 때 충전 폼 페이지로 이동
     */
    @GetMapping
    public String showChargeForm() {
        return "timeCharge";  // 뷰 이름 반환 → /WEB-INF/views/timeCharge.jsp로 포워딩됨
    }

    /**
     * ✅ 일반 폼 방식 충전 처리 (POST 요청)
     * timeCharge.jsp에서 폼으로 보낸 데이터를 받아서 서버에서 충전 처리
     */
    @PostMapping
    public String processCharge(@RequestParam("amount") int amount, // 충전할 시간(분)
                                HttpSession session, // 로그인 사용자 세션 정보
                                RedirectAttributes redirectAttributes) { // 리다이렉트 시 메시지 전달용 객체
        Member user = (Member) session.getAttribute("loggedInUser"); // 세션에서 로그인된 사용자 정보 가져오기
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다."); // 로그인 안됐으면 메시지 저장
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        if (amount <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "1분 이상부터 충전 가능합니다."); // 최소 1분 이상만 허용
            return "redirect:/charge"; // 다시 충전 페이지로 이동
        }

        try {
            purchaseService.updateAccountBalance(user.getMember_id(), amount); // DB에 사용자 시간 충전
            user.setAccount(user.getAccount() + amount); // 현재 세션의 사용자 잔액도 업데이트
            session.setAttribute("loggedInUser", user); // 세션 정보 갱신
            redirectAttributes.addFlashAttribute("successMessage", amount + "분이 성공적으로 충전되었습니다."); // 성공 메시지
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "충전 중 오류가 발생했습니다: " + e.getMessage()); // 예외 메시지 전달
        }

        return "redirect:/charge"; // 충전 페이지로 리다이렉트
    }

    /**
     * ✅ AJAX 충전 처리 (비동기 방식)
     * JS에서 AJAX로 /charge/ajax로 POST 요청 시 동작
     */
    @PostMapping("/ajax")
    @ResponseBody // 이 메서드는 View가 아니라 JSON 데이터를 직접 응답으로 보냄
    public Map<String, Object> ajaxCharge(@RequestParam("amount") int amount, HttpSession session) {
        Map<String, Object> response = new HashMap<>(); // 결과 응답을 담을 Map 생성

        Member user = (Member) session.getAttribute("loggedInUser"); // 세션에서 로그인 사용자 정보 가져오기
        if (user == null) {
            response.put("success", false); // 실패 응답
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        if (amount <= 0 || amount % 30 != 0) {
            response.put("success", false); // 30분 단위가 아니면 실패 응답
            response.put("message", "30분 단위로만 충전할 수 있습니다.");
            return response;
        }

        try {
            purchaseService.updateAccountBalance(user.getMember_id(), amount); // 실제 충전 처리 (DB 갱신)
            int newBalance = user.getAccount() + amount; // 새 잔액 계산
            user.setAccount(newBalance); // 세션 객체도 업데이트
            session.setAttribute("loggedInUser", user); // 세션 갱신

            response.put("success", true); // 성공 응답
            response.put("message", amount + "분이 성공적으로 충전되었습니다.");
            response.put("newAccount", newBalance); // 새로운 잔액도 함께 전달
        } catch (Exception e) {
            response.put("success", false); // 예외 발생 시 실패 응답
            response.put("message", "충전 중 오류가 발생했습니다: " + e.getMessage());
        }

        return response; // JSON 형식으로 응답
    }
}
