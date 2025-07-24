package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.CommentDTO;

import com.springmvc.domain.TalentDTO;
import com.springmvc.repository.CommentRepository;
import com.springmvc.repository.TalentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TalentRepository talentRepository;


    // 댓글 등록
    @Override
    public CommentDTO createComment(CommentDTO comment) {
        return commentRepository.createComment(comment); // ✅ 댓글 저장
    }

    // 특정 재능 게시글의 모든 댓글 조회
    @Override
    public List<CommentDTO> readAllComments(Long talentId) {
        return commentRepository.readAllComments(talentId);
    }

    // 댓글 수정 (내용만 변경)
    @Override
    public void updateComment(Long comment_id, String content) {
        commentRepository.updateComment(comment_id, content);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long comment_id) {
        commentRepository.deleteComment(comment_id);
    }
}
