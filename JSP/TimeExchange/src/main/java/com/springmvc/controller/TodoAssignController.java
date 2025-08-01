package com.springmvc.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.domain.Member;
import com.springmvc.domain.TodoDTO;
import com.springmvc.service.MemberService;
import com.springmvc.service.NotificationService;
import com.springmvc.service.TodoService;

@Controller
@RequestMapping("/todo/assign")
public class TodoAssignController {
	
	@Autowired
	TodoService todoService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	NotificationService notificationService;
    @GetMapping("/popup")
    public String popup(@RequestParam String tradeId,
                        @RequestParam String receiverId,
                        HttpSession session,
                        Model model) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            // 로그인 필요 처리
            model.addAttribute("tradeId", tradeId);
            model.addAttribute("receiverId", receiverId);
            model.addAttribute("writerId", "");
            return "todo/assign-popup"; // 팝업은 열리지만 전송 불가
        }
        model.addAttribute("tradeId", tradeId);
        model.addAttribute("receiverId", receiverId);
        model.addAttribute("writerId", user.getMember_id()); // 판매자(작성자)
        return "assign-popup"; // /WEB-INF/views/assign-popup.jsp
    }
    
    
    @PostMapping("/submit")
    @ResponseBody
    public Map<String, Object> submit(@RequestBody Map<String, Object> body, HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }

        try {
            // 파라미터 파싱
            Long tradeId = null;
            if (body.get("tradeId") != null && !String.valueOf(body.get("tradeId")).isBlank()) {
                tradeId = Long.valueOf(String.valueOf(body.get("tradeId")));
            }

            String writerId   = String.valueOf(body.get("writerId"));   // 판매자
            String receiverId = String.valueOf(body.get("receiverId")); // 구매자
            String title      = String.valueOf(body.get("title"));
            String content    = String.valueOf(body.getOrDefault("content",""));
            Integer priority  = (body.get("priority") != null) ? Integer.valueOf(String.valueOf(body.get("priority"))) : 2;

            java.time.LocalDate deadline = null;
            if (body.get("deadline") != null && !String.valueOf(body.get("deadline")).isBlank()) {
                deadline = java.time.LocalDate.parse(String.valueOf(body.get("deadline")));
            }

            // 간단한 권한/유효성 체크 (원하면 강화)
            if (!user.getMember_id().equals(writerId)) {
                return Map.of("success", false, "message", "작성자 정보가 올바르지 않습니다.");
            }
            if (title == null || title.isBlank()) {
                return Map.of("success", false, "message", "제목은 필수입니다.");
            }

            // DTO 조립
            TodoDTO dto = new TodoDTO();
            dto.setTradeId(tradeId);
            dto.setWriterId(writerId);
            dto.setReceiverId(receiverId);
            dto.setTitle(title);
            dto.setContent(content);
            dto.setPriority(priority != null ? priority : 2);
            dto.setCompleted(false);
            dto.setPersonal(false); // 숙제
            dto.setDeadline(deadline); // null이면 레포지토리에서 CURDATE()로 대체

            // 저장
            todoService.createTODO(dto); // 또는 todoRepository.createTODO(dto)
            
            String sender = memberService.findById(dto.getWriterId()).getUserName();
            String receiver = memberService.findById(dto.getReceiverId()).getUserName();
            notificationService.createSimpleNotification(
            		sender, 
            		receiver, 
            		"숙제", 
            		"숙제가 도착했습니다.", 
            		tradeId, 
            		"homework");
            return Map.of("success", true);
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }
}
 
