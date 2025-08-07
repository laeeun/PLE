package com.springmvc.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springmvc.config.AddressUtil;
import com.springmvc.domain.Member;
import com.springmvc.domain.TalentDTO;
import com.springmvc.service.TalentService;

@Controller
public class HomeController {

    @Autowired
    private TalentService talentService;

    @RequestMapping("/")
    public String main(Model model, HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");

        if (user != null) {
            String baseAddr = AddressUtil.extractBaseAddress(user.getAddr());
            String userId = user.getMember_id();
            List<TalentDTO> localTalents = talentService.getTalentByAddr(userId, baseAddr);
            model.addAttribute("localTalents", localTalents);
        }

        // 요청 수 저장용 맵 (카테고리별)
        Map<String, Integer> requestCountMap = new LinkedHashMap<>();
        Map<String, List<TalentDTO>> rankingMap = new LinkedHashMap<>();

        for (String category : categoryList) {
            List<TalentDTO> topTalents = talentService.getTopTalentsByRequestCount(category, 3);
            rankingMap.put(category, topTalents);

            int totalRequest = topTalents.stream()
                .mapToInt(TalentDTO::getRequestCount)
                .sum();
            requestCountMap.put(category, totalRequest);
        }

        List<Map.Entry<String, Integer>> sortedTop5 = requestCountMap.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .toList();

        model.addAttribute("categoryRankingMap", rankingMap);
        model.addAttribute("top5CategoryRanking", sortedTop5);
        model.addAttribute("categoryList", categoryList);

        return "home";
    }


    // 전체 카테고리 리스트
    private static final List<String> categoryList = Arrays.asList(
        "디자인", "프로그래밍", "번역", "음악", "영상편집", "글쓰기", "과외", "생활서비스", "기획창작"
    );
}
