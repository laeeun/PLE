package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.ReportDTO;

public class ReportRowMapper implements RowMapper<ReportDTO> {

    @Override
    public ReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReportDTO dto = new ReportDTO();

        dto.setReport_id(rs.getInt("report_id"));                     // 신고 고유 번호
        dto.setReporter_id(rs.getString("reporter_id"));              // 신고한 사용자 ID
        dto.setTarget_id(rs.getString("target_id"));                  // 신고 대상 사용자 ID
        dto.setTarget_type(rs.getString("target_type"));              // 신고 대상 종류 ('talent', 'comment')
        dto.setTarget_ref_id(rs.getInt("target_ref_id"));             // 신고 대상의 실제 ID
        dto.setReason(rs.getString("reason"));                        // 신고 사유
        dto.setStatus(rs.getString("status"));                        // 처리 상태
        dto.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());  // 생성일시
        if (rs.getTimestamp("processed_at") != null) {
            dto.setProcessed_at(rs.getTimestamp("processed_at").toLocalDateTime()); // 처리일시 (nullable)
        }
        dto.setAdmin_note(rs.getString("admin_note"));                // 관리자 메모

        return dto;
    }
}
