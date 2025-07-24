package com.springmvc.repository;

import com.springmvc.domain.HistoryDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryRowMapper implements RowMapper<HistoryDTO> {

    @Override
    public HistoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        HistoryDTO history = new HistoryDTO();
        history.setHistory_id(rs.getLong("history_id"));
        history.setBuyer_id(rs.getString("buyer_id"));
        history.setSeller_id(rs.getString("seller_id"));
        history.setTalent_id(rs.getLong("talent_id"));
        history.setCategory(rs.getString("category"));
        history.setAccount(rs.getInt("account"));
        history.setBalance_change(rs.getInt("balance_change"));
        history.setType(rs.getString("type"));
        history.setCreated_at(rs.getTimestamp("created_at"));

        // ✅ 리뷰 ID 추가 매핑 (LEFT JOIN이므로 null 가능)
        try {
            long reviewId = rs.getLong("review_id");
            if (!rs.wasNull()) {
                history.setReview_id(reviewId);
            } else {
                history.setReview_id(null);  // review_id가 없을 경우 null로
            }
        } catch (SQLException e) {
            history.setReview_id(null);
        }

        return history;
    }
}
