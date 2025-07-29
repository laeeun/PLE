package com.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.HistoryDTO;
import com.springmvc.domain.Member;
import com.springmvc.domain.ReviewDTO;
import com.springmvc.domain.ReviewReplyDTO;
import com.springmvc.service.HistoryService;
import com.springmvc.service.MemberService;
import com.springmvc.service.NotificationService;
import com.springmvc.service.ReviewService;

@RequestMapping("/review")
@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private HistoryService historyService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private MemberService memberService;
    // 리뷰 작성 폼
    @GetMapping("/form")
    public String reviewForm(@RequestParam String category,
                              @RequestParam String buyerId,
                              @RequestParam Long talentId,
                              @RequestParam String sellerId,
                              @RequestParam Long historyId,
                              Model model) {
        model.addAttribute("category", category);
        model.addAttribute("buyerId", buyerId);
        model.addAttribute("talentId", talentId);
        model.addAttribute("sellerId", sellerId);
        model.addAttribute("historyId", historyId);
        return "reviewForm";
    }

    // 리뷰 제출 처리
    @PostMapping("/submit")
    public String reviewSubmit(@RequestParam Long historyId,
                                @RequestParam String sellerId,
                                @RequestParam Long talentId,
                                @RequestParam String category,
                                @RequestParam int rating,
                                @RequestParam String comment,
                                HttpSession session,
                                RedirectAttributes ra) {

        Member login = (Member) session.getAttribute("loggedInUser");
        if (login == null) {
            return "redirect:/login";
        }

        String memberId = login.getMember_id();

        // 거래 확인 및 권한 체크
        HistoryDTO history = historyService.findById(historyId);
        if (history == null || 
            (!memberId.equals(history.getBuyer_id()) && !memberId.equals(history.getSeller_id()))) {
            ra.addFlashAttribute("error", "유효하지 않은 거래입니다.");
            return "redirect:/review/form";
        }

        // 🚨 거래 단위로 리뷰 중복 방지
        if (reviewService.existsByHistoryId(historyId)) {
            ra.addFlashAttribute("error", "이 거래에 대해 이미 리뷰를 작성하셨습니다.");
            return "redirect:/review/form";
        }

        // 리뷰 저장
        ReviewDTO dto = new ReviewDTO();
        dto.setHistoryId(historyId);
        dto.setWriterId(memberId);
        dto.setTalentId(talentId);
        dto.setTargetId(sellerId.trim());
        dto.setCategory(category);
        dto.setRating(rating);
        dto.setComment(comment);

        reviewService.save(dto);
        
        String sender = login.getUserName();
        String receiver = memberService.findById(sellerId).getUserName();
        notificationService.createSimpleNotification(sender, 
        					receiver, 
        					"리뷰", 
        					sender + "님이 당신에게 리뷰를 남겼습니다.", 
        					dto.getReviewId(), 
        					"review");      
        ra.addAttribute("id", dto.getReviewId());
        return "redirect:/review/myreviews";
    }

    // 리뷰 상세
    @GetMapping("/view")
    public String reviewDetail(@RequestParam Long id, HttpSession session, Model model) {
        ReviewDTO review = reviewService.findById(id);

        Member loggedInUser = (Member) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);


        if (loggedInUser != null) {
            String memberId = loggedInUser.getMember_id();
            String reaction = reviewService.findReviewReaction(id, memberId);
            review.setMyReaction(reaction);
        }

        model.addAttribute("review", review);
        return "review"; 
    }



    // 내가 쓴/받은 리뷰 리스트
    @GetMapping("/myreviews")
    public String myReviewList(HttpSession session, Model model) {
        Member login = (Member) session.getAttribute("loggedInUser");
        if (login == null) {
            return "redirect:/login";
        }

        String memberId = login.getMember_id();

        boolean isSeller = historyService.existsBySellerId(memberId);
        boolean isBuyer = historyService.existsByBuyerId(memberId);

        if (isBuyer) {
            List<ReviewDTO> writtenReviews = reviewService.findByWriterId(memberId);

            for (ReviewDTO review : writtenReviews) {
                review.setLikeCount(reviewService.countReviewLikes(review.getReviewId()));
                review.setDislikeCount(reviewService.countReviewDislikes(review.getReviewId()));
            }

            model.addAttribute("writtenReviews", writtenReviews);
        }

        if (isSeller) {
            List<ReviewDTO> receivedReviews = reviewService.findByTargetId(memberId);

            // 👍 좋아요/싫어요 개수 각 리뷰에 세팅
            for (ReviewDTO review : receivedReviews) {
                review.setLikeCount(reviewService.countReviewLikes(review.getReviewId()));
                review.setDislikeCount(reviewService.countReviewDislikes(review.getReviewId()));
            }

            model.addAttribute("receivedReviews", receivedReviews);
        }

        return "reviewList";
    }

    // 리뷰 수정 폼
    @GetMapping("/update")
    public String reviewUpdate(@RequestParam Long id, Model model) {
        ReviewDTO review = reviewService.findById(id);
        model.addAttribute("review", review);
        return "reviewUpdate";
    }

    // 리뷰 수정 처리
    @PostMapping("/update")
    public String reviewUpdate(@ModelAttribute ReviewDTO review) {
        reviewService.update(review);
        return "redirect:/review/myreviews";
    }

    // 리뷰 삭제
    @PostMapping("/delete")
    public String reivewDelete(@RequestParam Long id) {
        reviewService.delete(id);
        return "redirect:/review/myreviews";
    }

    // 답글 작성
    @PostMapping("/reply/submit")
    public String submitReply(@RequestParam Long reviewId,
                              @RequestParam String content,
                              HttpSession session,
                              RedirectAttributes ra) {

        Member seller = (Member) session.getAttribute("loggedInUser");
        if (seller == null) {
            return "redirect:/login";
        }

        ReviewDTO review = reviewService.findById(reviewId);
        if (!review.getTargetId().equals(seller.getMember_id())) {
            ra.addFlashAttribute("error", "답글 권한이 없습니다.");
            return "redirect:/review/myreviews";
        }

        ReviewReplyDTO reply = new ReviewReplyDTO();
        reply.setReviewId(reviewId);
        reply.setSellerId(seller.getMember_id());
        reply.setContent(content);

        reviewService.saveReply(reply);

        return "redirect:/review/myreviews";
    }

    // 답글 수정 폼
    @GetMapping("/reply/edit")
    public String editReply(@RequestParam Long reviewId, HttpSession session, Model model) {
        Member seller = (Member) session.getAttribute("loggedInUser");
        ReviewDTO review = reviewService.findById(reviewId);

        if (seller == null || !seller.getMember_id().equals(review.getTargetId())) {
            return "redirect:/review/view?id=" + reviewId;
        }

        model.addAttribute("review", review);
        model.addAttribute("editMode", true);
        return "review";
    }

    // 답글 수정 처리
    @PostMapping("/reply/update")
    public String updateReply(@RequestParam Long reviewId,
                              @RequestParam String content,
                              HttpSession session) {

        Member seller = (Member) session.getAttribute("loggedInUser");
        ReviewDTO review = reviewService.findById(reviewId);

        if (seller == null || !seller.getMember_id().equals(review.getTargetId())) {
            return "redirect:/review/view?id=" + reviewId;
        }

        ReviewReplyDTO reply = new ReviewReplyDTO();
        reply.setReviewId(reviewId);
        reply.setSellerId(seller.getMember_id());
        reply.setContent(content);

        reviewService.updateReply(reply);

        return "redirect:/review/view?id=" + reviewId;
    }

    // 답글 삭제
    @PostMapping("/reply/delete")
    public String deleteReply(@RequestParam Long reviewId, HttpSession session) {
        Member seller = (Member) session.getAttribute("loggedInUser");
        ReviewDTO review = reviewService.findById(reviewId);

        if (seller == null || !seller.getMember_id().equals(review.getTargetId())) {
            return "redirect:/review/view?id=" + reviewId;
        }

        reviewService.deleteReply(reviewId);
        return "redirect:/review/view?id=" + reviewId;
    }

    // 리뷰 좋아요/싫어요 토글
    @PostMapping("/reaction/toggle")
    @ResponseBody
    public String toggleReviewReaction(@RequestParam Long reviewId,
                                       @RequestParam String memberId,
                                       @RequestParam String reactionType,
                                       HttpSession session) {
    	
    	
    	System.out.println("📥 reviewId = " + reviewId);
        System.out.println("📥 memberId = " + memberId);
        System.out.println("📥 reactionType = " + reactionType);

        Member login = (Member) session.getAttribute("loggedInUser");
        if (login == null || !login.getMember_id().equals(memberId)) {
            return "unauthorized";
        }

        String current = reviewService.findReviewReaction(reviewId, memberId);
        if (reactionType.equals(current)) {
            reviewService.deleteReviewReaction(reviewId, memberId);
        } else {
            reviewService.saveReviewReaction(reviewId, memberId, reactionType);
        }

        return "success";
    }
}
