package com.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springmvc.domain.CommentDTO;
import com.springmvc.domain.Member;
import com.springmvc.service.CommentService;
import com.springmvc.service.MemberService;
import com.springmvc.service.NotificationService;
import com.springmvc.service.TalentService;

@RestController // 반환 값을 JSON으로 자동 변환해주는 컨트롤러
@RequestMapping("/comment") // 이 컨트롤러의 기본 URL: /comment
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    MemberService memberService;

    @Autowired
    TalentService talentService;

    /**
     * ✅ 댓글 목록 조회 (특정 재능글에 달린 모든 댓글 조회)
     * @param talentId 댓글을 조회할 대상 재능글 ID
     * @return CommentDTO 리스트(JSON)
     */
    @GetMapping("/list")
    public List<CommentDTO> getCommentsList(@RequestParam("talentId") Long talentId) {
        List<CommentDTO> list = commentService.readAllComments(talentId); // 댓글 조회
        return list;
    }

    /**
     * ✅ 댓글 등록 (AJAX로 JSON 전송)
     * @param comment 클라이언트가 보낸 댓글 정보 (JSON → DTO 자동 매핑)
     * @param session 현재 로그인한 사용자 세션
     * @return 처리 결과(status, message, 저장된 comment 객체)
     */
    @PostMapping("/add")
    public Map<String, Object> addComment(@RequestBody CommentDTO comment, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            Member user = (Member) session.getAttribute("loggedInUser"); // 로그인 사용자 가져오기
            if (user == null) {
                result.put("status", "error");
                result.put("message", "로그인이 필요합니다.");
                return result;
            }

            // 🔸 작성자 ID 설정
            comment.setWriterId(user.getMember_id());

            // 🔸 댓글이 달린 게시글의 작성자(member_id) 조회
            String receiverMemberId = talentService.readone(comment.getTalentId()).getMember_id();

            // 🔸 대상자(member_id → userName)로 알림용 이름 조회
            Member receiver = memberService.findById(receiverMemberId);
            String receiverUsername = receiver.getUserName();

            // 🔸 댓글 저장
            CommentDTO savedComment = commentService.createComment(comment);

            // 🔸 알림 발송 (자기 자신한테는 알림 X)
            if (!user.getMember_id().equals(receiverMemberId)) {
                notificationService.createSimpleNotification(
                    user.getUserName(),              // 보낸 사람
                    receiverUsername,               // 받는 사람
                    "댓글",                           // 알림 유형
                    user.getUserName() + "님이 댓글을 남겼습니다.", // 알림 메시지
                    comment.getTalentId(),          // 관련 글 ID
                    "post"                           // 관련 타입(post로 고정)
                );
            }

            // 🔸 결과 리턴
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

    /**
     * ✅ 댓글 수정 처리
     * @param comment 수정할 댓글 정보 (id + 수정된 content 포함)
     * @return 처리 결과
     */
    @PostMapping("/update")
    public Map<String, Object> updateComment(@RequestBody CommentDTO comment) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("updateComment");
            System.out.println(comment); // 디버깅용 로그

            // 🔸 댓글 내용만 수정
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

    /**
     * ✅ 댓글 삭제 처리
     * @param comment 삭제할 댓글 정보 (id만 사용)
     * @return 처리 결과
     */
    @PostMapping("/delete")
    public Map<String, Object> deleteComment(@RequestBody CommentDTO comment) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("deleteComment");
            System.out.println(comment); // 디버깅 로그

            // 🔸 댓글 ID 기반 삭제
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
