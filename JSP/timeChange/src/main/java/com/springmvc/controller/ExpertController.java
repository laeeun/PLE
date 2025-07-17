package com.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.domain.ExpertDTO;
import com.springmvc.domain.Member;
import com.springmvc.service.ExpertService;

@Controller
@RequestMapping("/expert")
public class ExpertController {

    @Autowired
    ExpertService expertService;

    // 전체 목록 조회 (페이징 처리)
    @GetMapping
    public String expertPage(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "6") int size,
                              Model model) {

        int offset = (page - 1) * size;
        List<ExpertDTO> expertList = expertService.readPaged(offset, size);
        int totalCount = expertService.countAll();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("expertList", expertList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("isSearch", false);
        model.addAttribute("category", "전체");
        return "expertPage";
    }

    // 단건 조회 (readOne)
    @GetMapping("/view")
    public String expertById(@RequestParam("id") Long expert_board_id, Model model) {
        ExpertDTO expert = expertService.readOne(expert_board_id);
        expertService.formatAvailableTime(expert);
        model.addAttribute("expert", expert);
        return "expertView";
    }

    // 등록 폼 (Create - Form)
    @GetMapping("/add")
    public String addExpertForm(Model model) {
        model.addAttribute("expert", new ExpertDTO());
        return "addExpert";
    }

    // 등록 처리 (Create - Submit)
    @PostMapping("/add")
    public String submitAddExpert(@ModelAttribute("expert") ExpertDTO expert,HttpSession session) {
    	 Member loggedInUser = (Member) session.getAttribute("loggedInUser");
    	 expert.setExpert_id(loggedInUser.getMember_id()); // 정확한 필드명으로 수정 필요
    	 System.out.println(loggedInUser);
    	 expertService.create(expert);
        return "redirect:/expert";
    }

    // 수정 폼 (Update - Form)
    @GetMapping("/update")
    public String updateExpertForm(@RequestParam("id") Long expert_board_id, Model model) {
        ExpertDTO expert = expertService.readOne(expert_board_id);
        model.addAttribute("expert", expert);
        return "updateExpertForm";
    }

    // 수정 처리 (Update - Submit)
    @PostMapping("/update")
    public String submitUpdate(@ModelAttribute("expert") ExpertDTO expert) {
        expertService.update(expert);
        return "redirect:/expert/view?id=" + expert.getExpert_board_id();
    }

    // 삭제 처리 (Delete)
    @GetMapping("/delete")
    public String deleteExpert(@RequestParam("id") Long expert_board_id) {
        expertService.delete(expert_board_id);
        return "redirect:/expert";
    }

    // 카테고리별 조회
    @GetMapping("/{category}")
    public String expertByCategory(@PathVariable("category") String category,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "6") int size,
                                   Model model) {

        List<ExpertDTO> expertList;
        int totalCount;

        int offset = (page - 1) * size;

        if ("전체".equals(category)) {
            expertList = expertService.readPaged(offset, size);
            totalCount = expertService.countAll();
        } else {
            expertList = expertService.readPagedCategory(category, page, size);
            totalCount = expertService.getCountByCategory(category);
        }

        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("expertList", expertList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("category", category);
        model.addAttribute("isSearch", false);
        return "expertPage";
    }

    // 검색 처리
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "6") int size,
                         Model model) {

        List<ExpertDTO> expertList = expertService.searchPagedExpert(keyword, page, size);
        int totalCount = expertService.countSearchResult(keyword);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("expertList", expertList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("isSearch", true);
        return "expertPage";
    }
}
