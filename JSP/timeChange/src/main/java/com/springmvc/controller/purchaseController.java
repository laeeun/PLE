package com.springmvc.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.springmvc.domain.PurchaseRequestDTO;
import com.springmvc.domain.TalentDTO;
import com.springmvc.service.HistoryService;
import com.springmvc.service.MemberService;
import com.springmvc.service.TalentService;
import com.springmvc.service.purchaseService;

@Controller
@RequestMapping("/purchase")
public class purchaseController {

    @Autowired
    TalentService talentService;

    @Autowired
    purchaseService purchaseService;
    
    @Autowired
    MemberService memberService;
    
    @Autowired
    HistoryService historyService;

    /**
     * [GET] /purchase?id=xx
     * 재능 구매 상세 페이지로 이동
     * - 선택한 재능 정보를 조회하여 purchase.jsp에 전달
     */
    @GetMapping
    public String talentPurchase(@RequestParam("id") int id, Model model) {
        TalentDTO talent = talentService.readone(id);
        talentService.formatTimeSlot(talent);  // 시간 포맷 적용
        model.addAttribute("talent", talent);
        return "purchase";  
    }

    /**
     * [POST] /purchase/request
     * 구매 요청 등록
     * - 로그인한 구매자 정보와 함께 DTO 저장
     */
    @PostMapping("/request")
    public String purchaseRequest(@ModelAttribute PurchaseRequestDTO dto,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        Member buyer = (Member) session.getAttribute("loggedInUser");
        dto.setBuyer_id(buyer.getMember_id());
        dto.setStatus("PENDING");
        dto.setRequested_at(LocalDateTime.now());

        // 💰 구매자 잔액 확인 및 판매자 ID 설정
        TalentDTO talent = talentService.readone(dto.getTalent_id());
        int price = talent.getTimeSlot();

        // ⛳ 판매자 ID 추가!
        dto.setSeller_id(talent.getMember_id());

        if (buyer.getAccount() < price) {
            redirectAttributes.addFlashAttribute("errorMessage", "잔액이 부족하여 구매 요청을 보낼 수 없습니다.");
            return "redirect:/purchase?id=" + dto.getTalent_id();
        }

        // 요청 저장
        purchaseService.save(dto);

        redirectAttributes.addFlashAttribute("successMessage", "구매 요청이 성공적으로 전송되었습니다.");
        return "redirect:/talent";
    }



    /**
     * [GET] /purchase/received
     * 판매자가 받은 구매 요청 목록 조회
     * - 로그인한 사용자가 판매자인 경우, 자신에게 들어온 요청들 보여줌
     */
    @GetMapping("/received")
    public String viewReceivedRequests(HttpSession session, Model model) {
        Member seller = (Member) session.getAttribute("loggedInUser");
        List<PurchaseRequestDTO> receivedList = purchaseService.findBySeller(seller.getMember_id());
        model.addAttribute("receivedRequests", receivedList);
        return "purchaseReceivedList";
    }

    /**
     * [GET] /purchase/sent
     * ➕ 구매자가 자신이 요청한 목록 조회
     * - 로그인한 사용자가 구매자인 경우, 자신이 보낸 요청들 보여줌
     */
    @GetMapping("/sent")
    public String viewSentRequests(HttpSession session, Model model) {
        Member buyer = (Member) session.getAttribute("loggedInUser");
        if (buyer == null) {
            model.addAttribute("errorMessage", "로그인 후 이용해 주세요.");
            return "redirect:/login";  // 또는 에러 페이지로 이동
        }
        
        List<PurchaseRequestDTO> sentList = purchaseService.findByBuyer(buyer.getMember_id());
        model.addAttribute("sentRequests", sentList);
        return "purchaseSentList";
    }

    /**
     * [POST] /purchase/approve
     * 판매자가 특정 구매 요청을 승인할 때 사용
     * - 요청 상태를 "APPROVED"로 변경
     */
    @PostMapping("/approve")
    public String approveRequest(@RequestParam("request_id") Long requestId,
                                 RedirectAttributes redirectAttributes) {
        try {
            // 1. 요청 상세 조회
            PurchaseRequestDTO request = purchaseService.findById(requestId);
            String buyerId = request.getBuyer_id();
            String sellerId = request.getSeller_id();
            long talentId = request.getTalent_id();

            // 2. 재능 정보 조회 (가격 확인)
            TalentDTO talent = talentService.readone((int) talentId);
            System.out.println(talent.getTimeSlot());
            int price = talent.getTimeSlot();

            // 3. 구매자 잔액 확인 (account 음수 방지 포함)
            Member buyer = memberService.findById(buyerId);
            if (buyer.getAccount() < price) {
                redirectAttributes.addFlashAttribute("errorMessage", "구매자의 잔액이 부족합니다.");
                return "redirect:/purchase/received";
            }

            // 4. account 업데이트
            purchaseService.updateAccountBalance(buyerId, -price);  // 구매자 차감
            purchaseService.updateAccountBalance(sellerId, price);  // 판매자 증가

            // 5. 상태 APPROVED로 변경
            purchaseService.updateStatus(requestId, "APPROVED");
            
            // ✅ 6. 히스토리 생성 및 저장 ------------------------
            HistoryDTO history = new HistoryDTO();
            history.setBuyer_id(buyerId);
            history.setSeller_id(sellerId);
            history.setTalent_id(talentId);
            history.setCategory(talent.getCategory());  // TalentDTO에 있어야 함
            history.setAccount(price);  // 거래 시간 == 가격
            history.setBalance_change(-price); // 구매자 기준
            history.setType("PURCHASE");

            historyService.save(history);  // 저장!
            
            // 7. 성공 메시지 추가
            redirectAttributes.addFlashAttribute("successMessage", "구매가 성공적으로 완료되었습니다!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/purchase/received";
        }

        return "redirect:/purchase/received";
    }

    

    /**
     * [POST] /purchase/reject
     * 판매자가 특정 구매 요청을 거절할 때 사용
     * - 요청 상태를 "REJECTED"로 변경
     */
    @PostMapping("/reject")
    public String rejectRequest(@RequestParam("request_id") Long requestId) {
        purchaseService.updateStatus(requestId, "REJECTED");
        return "redirect:/purchase/received";
    }
}
