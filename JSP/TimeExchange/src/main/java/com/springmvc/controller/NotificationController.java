package com.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springmvc.domain.Member;
import com.springmvc.domain.NotificationDTO;
import com.springmvc.service.NotificationService;

@RestController
@RequestMapping("/api")
public class NotificationController {

	@Autowired
	NotificationService notificationService;
	
	@PostMapping("/notifications/read")
	public void markAsRead(@RequestParam long id) {
	    notificationService.markAsRead(id);
	}

	@PostMapping("/notifications/read-all")
	public void markAllAsRead(HttpSession session) {
	    Member loginUser = (Member) session.getAttribute("loggedInUser");
	    if (loginUser != null) {
	        notificationService.markAllAsRead(loginUser.getUserName());
	    }
	}
	
	@PostMapping("/notifications/delete")
	public void deleteNotification(@RequestParam long id) {
	    notificationService.deleteNotification(id);
	}
	
	@GetMapping("/notifications/unread-count")
	public Map<String, Object> countUnread(HttpSession session) {
	    Map<String, Object> result = new HashMap<>();
	    Member loginUser = (Member) session.getAttribute("loggedInUser");
	    
	    if (loginUser != null) {
	        int count = notificationService.countUnread(loginUser.getUserName());
	        result.put("count", count);
	    } else {
	        result.put("count", 0);
	    }
	    
	    return result;
	}
	
	@PostMapping("/notifications/delete-multiple")
    public void deleteMultiple(@RequestParam("ids") String ids) {
        String[] idArray = ids.split(",");
        for (String idStr : idArray) {
            long id = Long.parseLong(idStr);
            notificationService.deleteNotification(id);
        }
    }
}
