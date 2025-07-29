package com.springmvc.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springmvc.domain.Member;
import com.springmvc.domain.TalentDTO;
import com.springmvc.service.NotificationService;
import com.springmvc.service.TalentService;

@ControllerAdvice
public class GlobalModelAttributeConfig {

    @Autowired
    private TalentService talentService;
    
    @Autowired
    private NotificationService notificationService;

    @ModelAttribute("top5CategoryRanking")
    public List<Map.Entry<String, Integer>> populateTop5Ranking() {
        Map<String, Integer> requestCountMap = new LinkedHashMap<>();

        // categoryList는 서비스에서 제공 (예: ["디자인", "프로그래밍", ...])
        List<String> categoryList = talentService.getAllCategories();

        for (String category : categoryList) {
            List<TalentDTO> topTalents = talentService.getTopTalentsByRequestCount(category, 3);

            int totalRequest = topTalents.stream()
                .mapToInt(TalentDTO::getRequestCount)
                .sum();

            requestCountMap.put(category, totalRequest);
        }

        return requestCountMap.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .collect(Collectors.toList());
    }
    
    @ModelAttribute("unreadCount")
    public int addUnreadCount(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loggedInUser");
        if (loginUser != null) {
            return notificationService.countUnread(loginUser.getUserName());
        }
        return 0;
    }
}
