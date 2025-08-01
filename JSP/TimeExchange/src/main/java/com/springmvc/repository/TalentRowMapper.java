package com.springmvc.repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
	    talent.setCreated_at(rs.getObject("created_at", LocalDateTime.class));
	    talent.setTimeSlot(rs.getInt("timeSlot"));
	    
	    try {
	        rs.findColumn("username");
	        talent.setUsername(rs.getString("username"));
	    } catch (SQLException ignore) {}

	    talent.setExpert(rs.getBoolean("expert"));

	    try {
	        rs.findColumn("request_count");
	        talent.setRequestCount(rs.getInt("request_count"));
	    } catch (SQLException ignore) {}

	    try {
	        talent.setAverageRating(rs.getDouble("averageRating"));
	    } catch (SQLException ignore) {
	        talent.setAverageRating(0.0);
	    }

	    try {
	        talent.setReviewCount(rs.getInt("reviewCount"));
	    } catch (SQLException ignore) {
	        talent.setReviewCount(0);
	    }
	    
	    try {
            rs.findColumn("file_name"); // 이 컬럼이 존재하는지 확인
            talent.setFilename(rs.getString("file_name")); // DTO에 넣기
        } catch (SQLException e) {
            // 컬럼이 없으면 무시 (다른 조회 쿼리에도 영향 없게)
        }
	    return talent;
	}
	
}
