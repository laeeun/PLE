package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.ReviewDTO;
import com.springmvc.domain.ReviewReplyDTO;

public class ReviewRowMapper implements RowMapper<ReviewDTO> {
    @Override
    public ReviewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReviewDTO review = new ReviewDTO();
        review.setReviewId(rs.getLong("review_id"));
        review.setWriterName(rs.getString("writer_name"));
        review.setTargetName(rs.getString("target_name"));
        review.setTalentId(rs.getLong("talent_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        review.setCategory(rs.getString("category"));
        review.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        review.setHistoryId(rs.getLong("history_id"));

        if (rs.getObject("reply_id") != null) {
            ReviewReplyDTO reply = new ReviewReplyDTO();
            reply.setReplyId(rs.getLong("reply_id"));
            reply.setReviewId(rs.getLong("review_id"));
            reply.setSellerId(rs.getString("seller_id"));
            reply.setReplyContent(rs.getString("reply_content")); // alias 사용
            reply.setReplyCreatedAt(rs.getObject("reply_created_at", LocalDateTime.class));
            review.setReply(reply);
        }

        return review;
    }
}


