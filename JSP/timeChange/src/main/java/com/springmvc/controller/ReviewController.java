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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.Member;
import com.springmvc.domain.ReviewDTO;
import com.springmvc.service.ReviewService;

@RequestMapping("/review")
@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;

	@GetMapping("/form")
	public String reviewForm(@RequestParam String category,
            @RequestParam String buyerId,
            @RequestParam Long talentId,
            @RequestParam String sellerId,
            @RequestParam Long historyId,
            Model model){
		
		model.addAttribute("category", category);
		model.addAttribute("buyerId", buyerId);
		model.addAttribute("talentId", talentId);
		model.addAttribute("sellerId", sellerId);
		model.addAttribute("historyId", historyId);
		System.out.println(sellerId);


		return "reviewForm";
	}
	
	@PostMapping("/submit")
	public String reviewSubmit(@RequestParam Long historyId,
	                           @RequestParam String sellerId,
	                           @RequestParam Long talentId,
	                           @RequestParam int rating,
	                           @RequestParam String comment,
	                           HttpSession session,
	                           RedirectAttributes ra) {
	    // 로그인한 사용자
	    Member login = (Member) session.getAttribute("loggedInUser");

	    // DTO 채우기
	    ReviewDTO dto = new ReviewDTO();
	    dto.setHistoryId(historyId);
	    dto.setWriterId(login.getMember_id());  
	    dto.setTargetId(sellerId.trim());              
	    dto.setTalentId(talentId);               
	    dto.setRating(rating);
	    dto.setComment(comment);

	    reviewService.save(dto);   

	    
	    ra.addAttribute("id", dto.getReviewId());
	    return "review"; 
	}
	
	@GetMapping("/view")
	public String reviewDetail(@RequestParam Long id, Model model) {
	    ReviewDTO review = reviewService.findById(id); 
	    model.addAttribute("review", review);       
	    return "review";
	}
	
	@GetMapping("/myreviews")
	public String myReviewList(HttpSession session, Model model) {
	    Member login = (Member) session.getAttribute("loggedInUser");

	    if (login == null) {
	        return "redirect:/login"; // 세션 없으면 로그인으로 보내기
	    }

	    String writerId = login.getMember_id();
	    
	    List<ReviewDTO> myReviews = reviewService.findByWriterId(writerId);
	    model.addAttribute("reviews", myReviews);

	    return "reviewList"; // 📄 보여줄 JSP 뷰 파일
	}

}
