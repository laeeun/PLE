package com.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.domain.Member;
import com.springmvc.domain.TalentDTO;
import com.springmvc.service.MemberService;
import com.springmvc.service.TalentService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    MemberService memberService;

    @Autowired
    TalentService talentService;

    /**
     * 특정 유저의 프로필 페이지를 조회하는 컨트롤러
     * - memberId를 기준으로 해당 사용자의 정보 및 재능 목록 조회
     * - 로그인한 사용자 정보도 함께 전달
     */
    @GetMapping
    public String getUserProfile(@RequestParam("id") String memberId, Model model, HttpSession session) {
        System.out.println("프로필 컨트롤러 호출됨");
        System.out.println("요청된 memberId: " + memberId);

        // 로그인한 사용자 정보 가져오기
        Member loginUser = (Member) session.getAttribute("loggedInUser");

        // 요청한 프로필 대상 사용자 정보 조회
        Member member = memberService.findById(memberId);

        // 해당 사용자가 등록한 재능 리스트 조회
        List<TalentDTO> talentlist = talentService.TalentByMemberId(memberId);

        // 모델에 정보 전달 (JSP 뷰에서 사용 가능)
        model.addAttribute("member", member);            // 조회 대상 사용자 정보
        model.addAttribute("loginUser", loginUser);      // 로그인 사용자 정보 (선택: JSP에서 sessionScope로 접근 가능)
        model.addAttribute("talentlist", talentlist);    // 해당 사용자의 재능 목록
        return "profile";  // → profile.jsp 렌더링
    }

}
