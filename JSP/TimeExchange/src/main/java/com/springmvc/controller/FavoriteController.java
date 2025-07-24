package com.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.domain.FavoriteDTO;
import com.springmvc.domain.Member;
import com.springmvc.service.FavoriteService;
import com.springmvc.service.TalentService;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {
	
	@Autowired
	FavoriteService favoriteService;
	@Autowired
	TalentService talentService;
	
	
	@GetMapping
	public String showFavoriteList(@RequestParam(value = "page", defaultValue = "1") int page,
	                               Model model, HttpSession session) {
	    Member user = (Member) session.getAttribute("loggedInUser");
	    if (user == null) return "redirect:/login";

	    int size = 6; // 한 페이지에 6개씩
	    int offset = (page - 1) * size;

	    List<FavoriteDTO> myFavoriteList = favoriteService.readPagedFavoriteList(user.getMember_id(), offset, size);
	    int totalCount = favoriteService.getFavoriteCount(user.getMember_id());
	    int totalPage = (int) Math.ceil((double) totalCount / size);

	    Map<String, Object> map = new HashMap<>();
	    map.put("myFavoriteList", myFavoriteList);
	    map.put("currentPage", page);
	    map.put("totalPage", totalPage);

	    model.addAllAttributes(map); // 여기서 한 번에 추가

	    return "myFavorite";
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, String> deleteFavorite(@RequestBody Map<String, Object> body, HttpSession session) {
	    Map<String, String> result = new HashMap<>();
	    try {
	        Member user = (Member) session.getAttribute("loggedInUser");
	        if (user == null) {
	            result.put("status", "unauthorized");
	            return result;
	        }

	        String memberId = user.getMember_id();
	        long talentId = Long.parseLong(body.get("talentId").toString());

	        if (favoriteService.exists(memberId, talentId)) {
	            favoriteService.deleteFavoriteTalent(memberId, talentId);
	            result.put("status", "success");
	        } else {
	            result.put("status", "not_found");
	        }
	    } catch (Exception e) {
	        result.put("status", "fail");
	        result.put("error", e.getMessage());
	    }
	    return result;
	}
	
	@PostMapping("/toggle")
	@ResponseBody
	public Map<String, String> toggleFavorite(@RequestBody Map<String, Object> body, HttpSession session) {
	    Map<String, String> result = new HashMap<>();

	    try {
	        Member user = (Member) session.getAttribute("loggedInUser");
	        if (user == null) {
	            result.put("status", "unauthorized");
	            return result;
	        }

	        String memberId = user.getMember_id();
	        long talentId = Long.parseLong(body.get("talentId").toString());

	        if (favoriteService.exists(memberId, talentId)) {
	            favoriteService.deleteFavoriteTalent(memberId, talentId);
	            result.put("status", "removed");
	        } else {
	            FavoriteDTO dto = new FavoriteDTO();
	            dto.setMemberId(memberId);
	            dto.setTalentId(talentId);
	            favoriteService.saveFavoriteTalent(dto);
	            result.put("status", "added");
	        }
	    } catch (Exception e) {
	        result.put("status", "fail");
	        result.put("error", e.getMessage());
	    }

	    return result;
	}

}
