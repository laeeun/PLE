package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.CommentDTO;

public interface CommentService {
	// 댓글 생성 (새 댓글 등록)
	CommentDTO createComment(CommentDTO comment);

    // 특정 재능 게시글에 대한 모든 댓글 조회
    List<CommentDTO> readAllComments(Long talentId);

    // 댓글 내용 수정 (updated_at은 SQL에서 자동 처리하거나 DTO에 포함)
    void updateComment(Long comment_id, String content);

    // 댓글 삭제 (해당 ID의 댓글 삭제)
    void deleteComment(Long comment_id);
}
