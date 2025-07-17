package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.PurchaseRequestDTO;
import com.springmvc.domain.Member;

@Repository
public class purchaseRepositoryImpl implements purchaseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public purchaseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(PurchaseRequestDTO dto) {
        String sql = "INSERT INTO purchase_request " +
                     "(talent_id, buyer_id, seller_id, status, requested_at) " +
                     "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            dto.getTalent_id(),
            dto.getBuyer_id(),
            dto.getSeller_id(),
            dto.getStatus(),
            dto.getRequested_at()
        );
    }

    @Override
    public PurchaseRequestDTO findById(Long requestId) {
        String sql = "SELECT * FROM purchase_request WHERE request_id = ?";
        return jdbcTemplate.queryForObject(sql, new PurchaseRowMapper(), requestId);
    }

    @Override
    public List<PurchaseRequestDTO> findBySeller(String sellerId) {
        String sql = "SELECT * FROM purchase_request WHERE seller_id = ? ORDER BY requested_at DESC";
        return jdbcTemplate.query(sql, new PurchaseRowMapper(), sellerId);
    }

    @Override
    public List<PurchaseRequestDTO> findByBuyer(String buyerId) {
        String sql = "SELECT * FROM purchase_request WHERE buyer_id = ? ORDER BY requested_at DESC";
        return jdbcTemplate.query(sql, new PurchaseRowMapper(), buyerId);
    }

    @Override
    public void deleteById(Long requestId) {
        String sql = "DELETE FROM purchase_request WHERE request_id = ?";
        jdbcTemplate.update(sql, requestId);
    }

    @Override
    public void updateStatus(Long requestId, String status) {
        String sql = "UPDATE purchase_request SET status = ?, approved_at = NOW() WHERE request_id = ?";
        jdbcTemplate.update(sql, status, requestId);
    }

    @Override
    public void updateAccountBalance(String memberId, int amount) {
        if (amount < 0) {
            // 음수일 경우 잔액 확인 후 처리
            String sqlCheck = "SELECT account FROM member WHERE member_id = ?";
            Integer currentBalance = jdbcTemplate.queryForObject(sqlCheck, Integer.class, memberId);

            if (currentBalance == null || currentBalance + amount < 0) {
                throw new IllegalArgumentException("잔액이 부족하여 차감할 수 없습니다.");
            }
        }

        // 잔액 업데이트
        String sqlUpdate = "UPDATE member SET account = account + ? WHERE member_id = ?";
        jdbcTemplate.update(sqlUpdate, amount, memberId);
    }
}
