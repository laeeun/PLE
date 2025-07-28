package com.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springmvc.domain.Member;
import com.springmvc.domain.TalentDTO;
import com.springmvc.service.FollowService;
import com.springmvc.service.MemberService;
import com.springmvc.service.TalentService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired 
    private MemberService memberService;
    @Autowired 
    private TalentService talentService;
    @Autowired 
    private FollowService followService;

    @GetMapping("/{memberId}")
    public String viewProfile(@PathVariable String memberId, Model model, HttpSession session) {
        Member login = (Member) session.getAttribute("loggedInUser");
        if (login == null) return "redirect:/login";

        Member profileUser = memberService.findById(memberId);
        List<TalentDTO> talentList = talentService.TalentByMemberId(memberId);
        boolean isFollowing = followService.exists(login.getMember_id(), memberId);

        model.addAttribute("member", profileUser);
        model.addAttribute("talentlist", talentList);
        model.addAttribute("isFollowing", isFollowing);
        model.addAttribute("loggedInUser", login);

        return "profile";
    }
}
