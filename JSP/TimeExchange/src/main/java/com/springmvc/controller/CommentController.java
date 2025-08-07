package com.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springmvc.domain.CommentDTO;
import com.springmvc.domain.Member;
import com.springmvc.service.CommentService;
import com.springmvc.service.MemberService;
import com.springmvc.service.NotificationService;
import com.springmvc.service.TalentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;
    
    @Autowired
    NotificationService notificationService;
    
    @Autowired
    MemberService memberService;
    
    @Autowired
    TalentService talentService;
    
    // [GET] 댓글 목록 조회 (특정 재능글의 모든 댓글)
    @GetMapping("/list")
    public List<CommentDTO> getCommentsList(@RequestParam("talentId") Long talentId) {
        List<CommentDTO> list = commentService.readAllComments(talentId);        
        return list;
    }

    // 댓글 등록
    @PostMapping("/add")
    public Map<String, Object> addComment(@RequestBody CommentDTO comment, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            Member user = (Member) session.getAttribute("loggedInUser");
            if (user == null) {
                result.put("status", "error");
                result.put("message", "로그인이 필요합니다.");
                return result;
            }

            // 댓글 작성자 ID 설정
            comment.setWriterId(user.getMember_id());

            // 댓글 대상자 (게시글 작성자) → 재능 ID로 member_id 조회
            String receiverMemberId = talentService.readone(comment.getTalentId()).getMember_id();

            // member_id → username 변환
            Member receiver = memberService.findById(receiverMemberId);
            String receiverUsername = receiver.getUserName();
            
            // 댓글 등록
            CommentDTO savedComment = commentService.createComment(comment);
            
            if (user.getMember_id() != receiverMemberId) {
	            notificationService.createSimpleNotification(
	                user.getUserName(),              // 보내는 유저
	                receiverUsername,                // 받는 유저
	                "댓글",
	                user.getUserName() + "님이 댓글을 남겼습니다.",
	                comment.getTalentId(),
	                "post"
	            );
            }
            result.put("status", "success");
            result.put("message", "댓글이 등록되었습니다.");
            result.put("comment", savedComment);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "댓글 등록 중 오류 발생");
        }
        return result;
    }


    // 댓글 수정
    @PostMapping("/update")
    public Map<String, Object> updateComment(@RequestBody CommentDTO comment) {
        Map<String, Object> result = new HashMap<>();
        try {
        	System.out.println("updateComment");
            System.out.println(comment);
            commentService.updateComment(comment.getCommentId(), comment.getContent());
            result.put("status", "success");
            result.put("message", "댓글이 수정되었습니다.");
        } catch (Exception e) {
        	e.printStackTrace();
            result.put("status", "error");
            result.put("message", "댓글 수정 중 오류 발생");
        }
        return result;
    }

    // 댓글 삭제
    @PostMapping("/delete")
    public Map<String, Object> deleteComment(@RequestBody CommentDTO comment) {
        Map<String, Object> result = new HashMap<>();
        try {
        	System.out.println("deleteComment");
            System.out.println(comment);
            commentService.deleteComment(comment.getCommentId());
            result.put("status", "success");
            result.put("message", "댓글이 삭제되었습니다.");
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "댓글 삭제 중 오류 발생");
        }
        return result;
    }
}
