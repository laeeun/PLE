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

@Controller
@RequestMapping("/charge")
public class ChargeController {

    @Autowired
    private purchaseService purchaseService;

    
    @GetMapping
    public String showChargeForm() {
        return "timeCharge";  // timeCharge.jsp
    }

   

    /**
     * ✅ AJAX 충전 처리
     */
    @PostMapping("/ajax")
    @ResponseBody
    public Map<String, Object> ajaxCharge(@RequestParam("amount") int amount, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        if (amount <= 0 || amount % 30 != 0) {
            response.put("success", false);
            response.put("message", "30분 단위로만 충전할 수 있습니다.");
            return response;
        }

        try {
            purchaseService.updateAccountBalance(user.getMember_id(), amount);
            int newBalance = user.getAccount() + amount;
            user.setAccount(newBalance);
            session.setAttribute("loggedInUser", user);

            response.put("success", true);
            response.put("message", amount + "분이 성공적으로 충전되었습니다.");      
            response.put("newAccount", newBalance);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "충전 중 오류가 발생했습니다: " + e.getMessage());
        }

        return response;
    }
}
