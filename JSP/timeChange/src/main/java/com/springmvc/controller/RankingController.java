package com.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springmvc.domain.RankingDTO;
import com.springmvc.service.RankingService;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    RankingService rankingService;

    // 전체 랭킹
    @GetMapping
    public String RankingList(Model model) {
        List<RankingDTO> ranklist = rankingService.getTopRankingList();
        model.addAttribute("ranklist", ranklist);
        return "rankingPage";
    }

    // 카테고리별 랭킹 (영문 카테고리 → 한글로 변환)
    @GetMapping("/{category}")
    public String RankingListByCategory(@PathVariable String category, Model model) {
        String koreanCategory = convertToKoreanCategory(category);
        List<RankingDTO> categorylist = rankingService.getTopRankingListByCategory(koreanCategory);
        model.addAttribute("ranklist", categorylist); // JSP에서는 동일 변수로 처리
        model.addAttribute("selectedCategory", koreanCategory); // 선택된 카테고리 표시용
        return "rankingPage";
    }

    // 카테고리명 영문 → 한글 변환 메서드
    private String convertToKoreanCategory(String category) {
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap.put("design", "디자인");
        categoryMap.put("programming", "프로그래밍");
        categoryMap.put("translate", "번역");
        categoryMap.put("music", "음악");
        categoryMap.put("video", "영상편집");
        categoryMap.put("writing", "글쓰기");
        categoryMap.put("lesson", "과외");
        categoryMap.put("service", "생활서비스");
        categoryMap.put("creative", "기획창작");
        categoryMap.put("all", "전체");

        return categoryMap.getOrDefault(category.toLowerCase(), "전체");
    }
}
