package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.PurchaseRequestDTO;

import java.time.LocalDateTime;

public class PurchaseRowMapper implements RowMapper<PurchaseRequestDTO> {

    @Override
    public PurchaseRequestDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PurchaseRequestDTO dto = new PurchaseRequestDTO();

        dto.setRequest_id(rs.getLong("request_id"));
        dto.setTalent_id(rs.getLong("talent_id"));
        dto.setBuyer_id(rs.getString("buyer_id"));
        dto.setSeller_id(rs.getString("seller_id"));
        dto.setStatus(rs.getString("status"));

        // DATETIME 타입은 getObject + LocalDateTime으로 받기
        dto.setRequested_at(rs.getObject("requested_at", LocalDateTime.class));
        dto.setApproved_at(rs.getObject("approved_at", LocalDateTime.class));

        return dto;
    }
}
