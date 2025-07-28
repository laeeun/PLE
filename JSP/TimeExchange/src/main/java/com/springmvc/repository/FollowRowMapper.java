package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.FollowDTO;

public class FollowRowMapper implements RowMapper<FollowDTO>{

	 @Override
	    public FollowDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        FollowDTO dto = new FollowDTO();

	        // 기본 follow 테이블 정보
	        try {
	            dto.setFollower_id(rs.getString("follower_id"));
	            dto.setFollowing_id(rs.getString("following_id"));
	            dto.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
	        } catch (SQLException ignored) {
	            // 컬럼이 없으면 무시
	        }

	        // member 테이블 JOIN 필드
	        try {
	            dto.setUserName(rs.getString("username"));
	            dto.setProfileImage(rs.getString("profile_image"));
	        } catch (SQLException ignored) {}

	        return dto;
	    }
	
}
