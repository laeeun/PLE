package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.CommentDTO;

public class CommentRowMapper implements RowMapper<CommentDTO>{

	@Override
	public CommentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CommentDTO dto = new CommentDTO();
		dto.setCommentId(rs.getLong("comment_id"));
		dto.setTalentId(rs.getLong("talent_id"));
		dto.setWriterId(rs.getString("writer_id"));
		dto.setContent(rs.getString("content"));
		dto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		dto.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
		dto.setUsername(rs.getString("username"));
		return dto;
	}
	
}
