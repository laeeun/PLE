package com.springmvc.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.HistoryDTO;
import com.springmvc.domain.Member;
import com.springmvc.domain.PurchaseRequestDTO;
import com.springmvc.domain.TalentDTO;
import com.springmvc.service.HistoryService;
import com.springmvc.service.MemberService;
import com.springmvc.service.NotificationService;
import com.springmvc.service.TalentService;
import com.springmvc.service.purchaseService;

@Controller
@RequestMapping("/purchase")
public class purchaseController {

    @Autowired
    private TalentService talentService;

    @Autowired
    private purchaseService purchaseService;

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private NotificationService notificationService;
    
    @GetMapping
    public String talentPurchase(@RequestParam("id") int id, Model model) {
        TalentDTO talent = talentService.readone(id);
        talentService.formatTimeSlot(talent);
        model.addAttribute("talent", talent);
        return "purchase";
    }

    @PostMapping("/request")
    public String purchaseRequest(@ModelAttribute PurchaseRequestDTO dto,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        Member buyer = (Member) session.getAttribute("loggedInUser");
        dto.setBuyer_id(buyer.getMember_id());
        dto.setStatus("PENDING");
        dto.setRequested_at(LocalDateTime.now());

        TalentDTO talent = talentService.readone(dto.getTalent_id());
        int price = talent.getTimeSlot();
        dto.setSeller_id(talent.getMember_id());

        if (buyer.getAccount() < price) {
            redirectAttributes.addFlashAttribute("errorMessage", "잔액이 부족하여 구매 요청을 보낼 수 없습니다.");
            return "redirect:/purchase?id=" + dto.getTalent_id();
        }
        String sender = buyer.getUserName();  // ✅ 구매자 → 알림을 보낸 사람
        String receiver = memberService.findById(dto.getSeller_id()).getUserName();  // ✅ 판매자 → 알림을 받는 사람
       
        purchaseService.save(dto);
        notificationService.createSimpleNotification(sender, 
        		receiver, 
        		"재능구매요청", 
        		"구매요청이 도착했습니다.", 
        		dto.getRequest_id(), 
        		"purchase");
        redirectAttributes.addFlashAttribute("successMessage", "구매 요청이 성공적으로 전송되었습니다.");
        return "redirect:/talent";
    }

    @GetMapping("/received")
    public String viewReceivedRequests(HttpSession session, Model model) {
        Member seller = (Member) session.getAttribute("loggedInUser");
        List<PurchaseRequestDTO> receivedList = purchaseService.findBySeller(seller.getMember_id());
        model.addAttribute("receivedRequests", receivedList);
        return "purchaseReceivedList";
    }

    @GetMapping("/sent")
    public String viewSentRequests(HttpSession session, Model model) {
        Member buyer = (Member) session.getAttribute("loggedInUser");
        if (buyer == null) {
            model.addAttribute("errorMessage", "로그인 후 이용해 주세요.");
            return "redirect:/login";
        }

        List<PurchaseRequestDTO> sentList = purchaseService.findByBuyer(buyer.getMember_id());
        model.addAttribute("sentRequests", sentList);
        return "purchaseSentList";
    }

    @PostMapping("/approve/ajax")
    @ResponseBody
    public Map<String, Object> approveRequestAjax(@RequestParam("request_id") Long requestId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            PurchaseRequestDTO request = purchaseService.findById(requestId);
            String buyerId = request.getBuyer_id();
            String sellerId = request.getSeller_id();
            long talentId = request.getTalent_id();

            TalentDTO talent = talentService.readone((int) talentId);
            int price = talent.getTimeSlot();

            Member buyer = memberService.findById(buyerId);
            if (buyer.getAccount() < price) {
                response.put("success", false);
                response.put("message", "구매자의 잔액이 부족합니다.");
                return response;
            }

            purchaseService.updateAccountBalance(buyerId, -price);
            Member updatedSeller = memberService.updateAccountAndReturn(sellerId, price);
            purchaseService.updateStatus(requestId, "APPROVED");
            
            HistoryDTO history = new HistoryDTO();
            history.setBuyer_id(buyerId);
            history.setSeller_id(sellerId);
            history.setTalent_id(talentId);
            history.setCategory(talent.getCategory());  // TalentDTO에 있어야 함
            history.setAccount(price);  // 거래 시간 == 가격
            history.setBalance_change(-price); // 구매자 기준
            history.setType("PURCHASE");

            historyService.save(history);  // 저장!
            
            //알람 전송
            String sender  = memberService.findById(sellerId).getUserName();           
            notificationService.createSimpleNotification(sender, 
            		buyer.getUserName(), 
            		"재능구매", 
            		"구매요청이 승인되었습니다.", 
            		requestId, 
            		"purchase");
            
            // 세션 갱신
            session.setAttribute("loggedInUser", updatedSeller);        
            response.put("success", true);
            response.put("message", "구매 요청이 승인되었습니다.");
            response.put("updatedAccount", updatedSeller.getAccount());
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "승인 중 오류: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/reject")
    public String rejectRequest(@RequestParam("request_id") Long requestId) {
        purchaseService.updateStatus(requestId, "REJECTED");
        PurchaseRequestDTO request = purchaseService.findById(requestId);
        String buyer = request.getBuyer_id();
        String seller = request.getSeller_id();
        
        String sender = memberService.findById(seller).getUserName();   // ✅ 판매자
        String receiver = memberService.findById(buyer).getUserName();  // ✅ 구매자
        
        notificationService.createSimpleNotification(sender, 
        					receiver, 
        					"재능구매", 
        					"구매요청이 거절되었습니다.", 
        					requestId, 
        					"purchase");
        return "redirect:/purchase/received";
    }
}
