package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.CommentDTO;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    JdbcTemplate template;

    @Autowired
    public CommentRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    // 댓글 생성
    @Override
    public CommentDTO createComment(CommentDTO comment) {
        String sql = "INSERT INTO comment (talent_id, writer_id, content, created_at, updated_at) " +
                     "VALUES (?, ?, ?, NOW(), NOW())";
        template.update(sql,
            comment.getTalentId(),
            comment.getWriterId(),
            comment.getContent()
        );

        // 마지막 insert된 comment_id 가져오기
        String lastIdSql = "SELECT LAST_INSERT_ID()";
        Long newCommentId = template.queryForObject(lastIdSql, Long.class);

        // 다시 조회하여 username 포함된 CommentDTO 리턴
        return readOneComment(newCommentId);
    }

    // 댓글 단일 조회
    @Override
    public CommentDTO readOneComment(Long commentId) {
        String sql = "SELECT c.comment_id, c.talent_id, c.writer_id, m.username, " +
                     "c.content, c.created_at, c.updated_at " +
                     "FROM comment c " +
                     "JOIN member m ON c.writer_id = m.member_id " +
                     "WHERE c.comment_id = ?";
        return template.queryForObject(sql, new CommentRowMapper(), commentId);
    }

    // 특정 재능글의 모든 댓글 조회
    @Override
    public List<CommentDTO> readAllComments(Long talentId) {
        String sql = "SELECT c.comment_id, c.talent_id, c.writer_id, m.username, " +
                     "c.content, c.created_at, c.updated_at " +
                     "FROM comment c " +
                     "JOIN member m ON c.writer_id = m.member_id " +
                     "WHERE c.talent_id = ? " +
                     "ORDER BY c.created_at ASC";
        return template.query(sql, new CommentRowMapper(), talentId);
    }

    // 댓글 수정
    @Override
    public void updateComment(Long commentId, String content) {
        String sql = "UPDATE comment SET content = ?, updated_at = NOW() WHERE comment_id = ?";
        template.update(sql, content, commentId);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        String sql = "DELETE FROM comment WHERE comment_id = ?";
        template.update(sql, commentId);
    }
}
