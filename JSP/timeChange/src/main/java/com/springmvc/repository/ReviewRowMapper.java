package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.ReviewDTO;

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
    	review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return review;
    }
}

