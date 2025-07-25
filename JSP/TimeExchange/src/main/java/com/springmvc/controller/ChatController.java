package com.springmvc.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springmvc.domain.ChatListDTO;
import com.springmvc.domain.ChatMessage;
import com.springmvc.service.ChatService;

@Controller
public class ChatController {

	@Autowired
    private ChatService chatService;


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        message.setCreatedAt(LocalDateTime.now()); // 보낸 시간 설정
        chatService.saveMessage(message); // DB 저장
        return message; // 클라이언트에게 전송
    }
    
    @GetMapping("/chat/list")
    public String chatListPage(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("loggedInId");
        List<ChatListDTO> chatList = chatService.getChatListByMemberId(memberId);
        model.addAttribute("chatList", chatList);
        return "chatList"; 
    }
    
    
}
