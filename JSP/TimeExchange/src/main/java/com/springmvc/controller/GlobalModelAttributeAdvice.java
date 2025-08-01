package com.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.springmvc.domain.Member;
import com.springmvc.service.ChatService;

@ControllerAdvice
public class GlobalModelAttributeAdvice {

    @Autowired
    private ChatService chatService;

    @ModelAttribute
    public void addUnreadCount(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // ❗ 세션이 없으면 null 반환

        if (session != null) {
            Member loginUser = (Member) session.getAttribute("loggedInUser");

            if (loginUser != null) {
                int unreadCount = chatService.countUnreadMessages(loginUser.getMember_id());
                model.addAttribute("unreadCount1", unreadCount);
            }
        }
    }
}
