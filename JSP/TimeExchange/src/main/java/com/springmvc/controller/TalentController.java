package com.springmvc.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.domain.Member;
import com.springmvc.domain.TalentDTO;
import com.springmvc.service.FavoriteService;
import com.springmvc.service.ReviewService;
import com.springmvc.service.TalentService;

@Controller
public class TalentController {

    @Autowired
    private TalentService talentService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private ReviewService reviewService;

    // ✅ 재능 목록 + 필터 + 검색 통합
    @GetMapping("/talent")
    public String talentList(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "6") int size,
        @RequestParam(value = "expert", defaultValue = "all") String expert,
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "keyword", required = false) String keyword,
        Model model,
        HttpSession session) {

        Member user = (Member) session.getAttribute("loggedInUser");

        List<TalentDTO> list = talentService.readTalents(page, size, expert, category, keyword);
        int totalCount = talentService.getTalentCount(expert, category, keyword);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        for (TalentDTO dto : list) {
            talentService.formatTimeSlot(dto);
        }

        attachFavoriteInfo(list, user);

        model.addAttribute("talentList", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("expert", expert);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);

        return "talent";
    }

    // ✅ /talent/카테고리 요청 → redirect:/talent?category=카테고리
    @GetMapping("/talent/{category}")
    public String redirectCategory(@PathVariable("category") String category) {
        return "redirect:/talent?category=" + category;
    }

    // ✅ 재능 상세 보기
    @GetMapping("/talent/view")
    public String viewTalent(@RequestParam("id") int id, Model model, HttpSession session) {
    	TalentDTO talent = talentService.readone(id);
    	LocalDateTime ldt = talent.getCreated_at();
    	Date createdDate = java.sql.Timestamp.valueOf(ldt);
    	Member user = (Member) session.getAttribute("loggedInUser");

    	talentService.formatTimeSlot(talent);
    	boolean isFavorite = user != null && favoriteService.exists(user.getMember_id(), id);

    	// ⭐ 리뷰 정보 조회
    	int reviewCount = reviewService.countByTalentId((long) id);
    	double averageRating = reviewService.getAverageRatingByTalentId((long) id);

    	// 🧾 Model에 값 추가
    	model.addAttribute("isFavorite", isFavorite);
    	model.addAttribute("talent", talent);
    	model.addAttribute("createdDate", createdDate);
    	model.addAttribute("reviewCount", reviewCount);
    	model.addAttribute("averageRating", averageRating);

    	return "viewTalent";
    }

    // ✅ 재능 등록 폼 요청
    @GetMapping("/addtalent")
    public String requestAddTalentForm(@ModelAttribute("newTalent") TalentDTO dto) {
        return "addTalent";
    }

    // ✅ 재능 등록 처리
    @PostMapping("/addtalent")
    public String submitTalentForm(@ModelAttribute("newTalent") TalentDTO dto, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loggedInUser");
        dto.setMember_id(loginUser.getMember_id());
        dto.setCreated_at(LocalDateTime.now());
        talentService.formatTimeSlot(dto);
        talentService.create(dto);
        return "redirect:/talent";
    }

    // ✅ 재능 삭제
    @GetMapping("/talent/delete")
    public String deleteTalent(@RequestParam("id") int id) {
        talentService.delete(id);
        return "redirect:/talent";
    }

    // ✅ 재능 수정 폼 요청
    @GetMapping("/talent/update")
    public String getUpdateTalentForm(@RequestParam("id") int id, Model model) {
        TalentDTO talent = talentService.readone(id);
        model.addAttribute("updateTalent", talent);
        return "updateForm";
    }

    // ✅ 재능 수정 처리
    @PostMapping("/talent/update")
    public String submitUpdate(@ModelAttribute("updateTalent") TalentDTO dto) {
        talentService.update(dto);
        return "redirect:/talent";
    }

    // ✅ 로그인 유저 기준 찜 여부 세팅
    private void attachFavoriteInfo(List<TalentDTO> list, Member user) {
        if (user == null) return;
        for (TalentDTO dto : list) {
            boolean isFavorite = favoriteService.exists(user.getMember_id(), dto.getTalent_id());
            dto.setFavorite(isFavorite);
        }
    }
}
