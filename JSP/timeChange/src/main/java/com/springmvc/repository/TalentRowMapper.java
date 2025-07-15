package com.springmvc.repository;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.TalentDTO;



public class TalentRowMapper implements RowMapper<TalentDTO>{

	@Override
	public TalentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		TalentDTO talent = new TalentDTO();
        talent.setTalent_id(rs.getLong("talent_id"));
        talent.setMember_id(rs.getString("member_id"));
        talent.setTitle(rs.getString("title"));
        talent.setDescription(rs.getString("description"));
        talent.setCategory(rs.getString("category"));
        talent.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        talent.setTimeSlot(rs.getInt("timeSlot"));
        talent.setUsername(rs.getString("username"));
        talent.setExpert(rs.getBoolean("expert"));
        return talent;
	}
	
}
