package com.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.domain.FollowDTO;
import com.springmvc.domain.Member;
import com.springmvc.service.FollowService;

@Controller
@RequestMapping("/follow")
public class FollowController {
	
	@Autowired
	FollowService followService;
	
	@PostMapping("/toggle")
    @ResponseBody
    public String toggleFollow(@RequestParam String followingId, HttpSession session) {
        Member login = (Member) session.getAttribute("loggedInUser");
        if (login == null) return "unauthorized";

        boolean isNowFollowing = followService.toggleFollow(login.getMember_id(), followingId);
        return isNowFollowing ? "followed" : "unfollowed";
    }
	
	
	@GetMapping("/following")
    public String showFollowingList(Model model, HttpSession session) {
        Member login = (Member) session.getAttribute("loggedInUser");
        if (login == null) return "redirect:/login";

        List<FollowDTO> list = followService.findFollowingList(login.getMember_id());
        model.addAttribute("followingList", list);
        return "followingList"; // 💡 JSP로 이동
	}

    // ✅ 나를 팔로우한 유저 목록 (팔로워 보기)
    @GetMapping("/followers")
    public String showFollowerList(Model model, HttpSession session) {
        Member login = (Member) session.getAttribute("loggedInUser");
        if (login == null) return "redirect:/login";

        List<FollowDTO> list = followService.findFollowerList(login.getMember_id());
        model.addAttribute("followerList", list);
        return "followerList";
    }
}
