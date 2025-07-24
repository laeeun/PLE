package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.ReviewDTO;
import com.springmvc.domain.ReviewReplyDTO;

public class ReviewRowMapper implements RowMapper<ReviewDTO> {
    @Override
    public ReviewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ReviewDTO review = new ReviewDTO();
    	review.setReviewId(rs.getLong("review_id"));
    	review.setWriterId(rs.getString("writer_id"));
    	review.setTargetId(rs.getString("target_id"));
    	review.setTalentId(rs.getLong("talent_id"));
    	review.setRating(rs.getInt("rating"));
    	review.setComment(rs.getString("comment"));
    	review.setCategory(rs.getString("category"));
    	review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
    	review.setHistoryId(rs.getLong("history_id"));
    	
    	
    	 if (rs.getObject("reply_id") != null) {
             ReviewReplyDTO reply = new ReviewReplyDTO();
             reply.setReplyId(rs.getLong("reply_id"));
             reply.setReviewId(rs.getLong("review_id"));
             reply.setSellerId(rs.getString("reply_seller_id"));
             reply.setContent(rs.getString("reply_content"));
             reply.setCreatedAt(rs.getTimestamp("reply_created_at").toLocalDateTime());
             
             review.setReply(reply); // ReviewDTO에 답글 객체 설정
    	 }
        return review;
    }
}

