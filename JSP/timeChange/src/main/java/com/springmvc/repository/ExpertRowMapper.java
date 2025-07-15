package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.springmvc.domain.ExpertDTO;

public class ExpertRowMapper implements RowMapper<ExpertDTO>{

	@Override
	public ExpertDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExpertDTO expert = new ExpertDTO();
		expert.setExpert_board_id(rs.getLong("expert_board_id"));
		expert.setExpert_id(rs.getString("expert_id"));
		expert.setTitle(rs.getString("title"));
		expert.setContent(rs.getString("content"));
		expert.setCategory(rs.getString("category"));
		expert.setPrice(rs.getInt("price"));
		expert.setAvailable_time(rs.getString("available_time"));
		expert.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
		expert.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
		
		return expert;
	}
	
}
