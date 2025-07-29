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
    public String main(Model model) {
        // 요청 수 저장용 맵 (카테고리별)
        Map<String, Integer> requestCountMap = new LinkedHashMap<>();
        // 재능 목록 저장용 맵 (카테고리별 상위 3개)
        Map<String, List<TalentDTO>> rankingMap = new LinkedHashMap<>();

        for (String category : categoryList) {
            // 카테고리별 요청 수 상위 3개 재능 가져오기
            List<TalentDTO> topTalents = talentService.getTopTalentsByRequestCount(category, 3);
            rankingMap.put(category, topTalents); // 재능 저장

            // 해당 카테고리의 총 요청 수 계산
            int totalRequest = topTalents.stream()
                .mapToInt(TalentDTO::getRequestCount)
                .sum();
            requestCountMap.put(category, totalRequest); // 요청 수 저장
        }

        // 요청 수 기준 내림차순 정렬 후 상위 5개만 추출
        List<Map.Entry<String, Integer>> sortedTop5 = requestCountMap.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .toList();

        // 모델에 값 저장 (뷰로 전달)
        model.addAttribute("categoryRankingMap", rankingMap);      // 전체 카테고리 목록
        model.addAttribute("top5CategoryRanking", sortedTop5);     // 상위 5개 카테고리 (드롭다운용)
        model.addAttribute("categoryList", categoryList);          // 전체 카테고리 이름 리스트
        return "home"; // home.jsp 뷰로 이동
    }
    //로그인한 유저 지역에 맞는 재능 리스트
    //******************아직 테스트 안했음******************
    @GetMapping
    public String showLocalTalents(HttpSession session, Model model) {
    	Member user = (Member) session.getAttribute("loggedInUser");

	    if (user == null) {
	        return "redirect:/login";
	    }
	    String baseAddr = AddressUtil.extractBaseAddress(user.getAddr());
	    String userId = user.getMember_id();

	    List<TalentDTO> localTalents = talentService.getTalentByAddr(userId, baseAddr);
	    
	    model.addAttribute("localTalents", localTalents);
        return "home";
    }

    // 전체 카테고리 리스트
    private static final List<String> categoryList = Arrays.asList(
        "디자인", "프로그래밍", "번역", "음악", "영상편집", "글쓰기", "과외", "생활서비스", "기획창작"
    );
}
