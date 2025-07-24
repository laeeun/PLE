package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.FavoriteDTO;

public class FavoriteRowMapper implements RowMapper<FavoriteDTO>{

	@Override
	public FavoriteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FavoriteDTO dto = new FavoriteDTO();
        dto.setFavoriteId(rs.getLong("favorite_id"));
        dto.setMemberId(rs.getString("member_id"));
        dto.setTalentId(rs.getLong("talent_id"));
        dto.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());

        dto.setTitle(rs.getString("title"));
        dto.setCategory(rs.getString("category"));
        dto.setDescription(rs.getString("description"));
        dto.setTimeSlot(rs.getInt("timeSlot"));
		return dto;
	}
	
}
