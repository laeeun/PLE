package com.springmvc.repository;

import com.springmvc.domain.ExpertProfileDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ExpertProfileRowMapper implements RowMapper<ExpertProfileDTO> {

    @Override
    public ExpertProfileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ExpertProfileDTO dto = new ExpertProfileDTO();

        dto.setId(rs.getLong("id"));
        dto.setMemberId(rs.getString("member_id"));
        dto.setCareer(rs.getString("career"));
        dto.setUniversity(rs.getString("university"));
        dto.setCertification(rs.getString("certification"));

        // ✅ 자기소개 매핑 추가!
        dto.setIntroduction(rs.getString("introduction"));

        // file_names는 쉼표 구분 문자열로 저장되어 있다고 가정
        String files = rs.getString("file_names");
        if (files != null && !files.trim().isEmpty()) {
            List<String> fileList = Arrays.asList(files.split(","));
            dto.setFileNames(fileList);
        }

        // DATETIME → LocalDateTime 변환
        dto.setSubmittedAt(rs.getTimestamp("submitted_at") != null
                ? rs.getTimestamp("submitted_at").toLocalDateTime() : null);

        return dto;
    }
}
