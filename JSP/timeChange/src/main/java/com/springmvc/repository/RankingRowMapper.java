package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.RankingDTO;

public class RankingRowMapper implements RowMapper<RankingDTO>{

	@Override
	public RankingDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		RankingDTO dto = new RankingDTO();
		dto.setRanking_id(rs.getLong("ranking_id"));           // 기본 키
	    dto.setTalent_id(rs.getLong("talent_id"));             // 재능 ID
	    dto.setMember_id(rs.getString("member_id"));           // 회원 ID
	    dto.setTotal_sales(rs.getInt("total_sales"));          // 총 판매 횟수
	    dto.setScore(rs.getDouble("score"));                   // 점수
	    dto.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());  // 생성 시각
	    dto.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());  // 갱신 시각
	    dto.setMember_nickname(rs.getString("username"));
	    dto.setMember_nickname(rs.getString("category"));
		return dto;
	}
	
}
	