package com.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.springmvc.domain.Member;
import com.springmvc.domain.NotificationDTO;
import com.springmvc.service.NotificationService;

@Controller
@RequestMapping("/notification")
public class NotificationPageController {

    @Autowired
    private NotificationService notificationService;
    
    private Map<String, Function<Long, String>> redirectMap = new HashMap<>();

    @GetMapping("/popup")
    public String showNotificationPopup(HttpSession session,
                                        @RequestParam(value = "type", required = false) String type,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size,
                                        Model model) {
        Member loginUser = (Member) session.getAttribute("loggedInUser");
        
        
        if (loginUser == null) {
            return "redirect:/login";
        }
        notificationService.markAllAsRead(loginUser.getUserName());
        String username = loginUser.getUserName();
        int offset = (page - 1) * size;
        
        List<NotificationDTO> notifications = notificationService.getNotificationsByTypeAndPage(username, type, offset, size);
        int total = notificationService.countNotifications(username, type);
        int lastPage = (int) Math.ceil((double) total / size);
        
        model.addAttribute("notifications", notifications);
        model.addAttribute("total", total);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("type", type);
        model.addAttribute("lastPage", lastPage);
        
        return "notificationPopup"; 
    }
    
    @PostConstruct
    public void initRedirectMap() {
        redirectMap.put("댓글", id -> UriComponentsBuilder.fromPath("/talent/view").queryParam("id", id).toUriString());
        redirectMap.put("재능등록", id -> UriComponentsBuilder.fromPath("/talent/view").queryParam("id", id).toUriString());
        redirectMap.put("채팅", id -> UriComponentsBuilder.fromPath("/chat/room").queryParam("roomId", id).toUriString());
        redirectMap.put("재능구매", id -> UriComponentsBuilder.fromPath("/purchase/sent").toUriString());
        redirectMap.put("재능구매요청", id -> UriComponentsBuilder.fromPath("/purchase/received").toUriString());
        redirectMap.put("팔로우", id -> UriComponentsBuilder.fromPath("/follow/followers").toUriString());
        redirectMap.put("리뷰", id -> UriComponentsBuilder.fromPath("/review/myreviews").toUriString());
        redirectMap.put("숙제", id -> UriComponentsBuilder.fromPath("/todo").toUriString());
    }
    
    @GetMapping("/go/{id}")
    @ResponseBody
    public String getRedirectUrl(@PathVariable Long id, HttpServletRequest request) {
        NotificationDTO noti = notificationService.getDetailNotification(id);
        if (noti == null) return "/";

        Long refId = noti.getTargetId();
        String type = noti.getType();
        String ctx = request.getContextPath();

        Function<Long, String> pathFunction = redirectMap.get(type);
        if (pathFunction == null) {
            System.err.println("⚠ 알 수 없는 알림 type: " + type);
            return ctx + "/";
        }

        String relativePath = pathFunction.apply(refId);
        return ctx + relativePath;
    }
   
}
