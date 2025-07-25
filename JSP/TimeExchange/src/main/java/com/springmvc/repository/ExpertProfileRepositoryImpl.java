package com.springmvc.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.ExpertProfileDTO;

@Repository
public class ExpertProfileRepositoryImpl implements ExpertProfileRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper rowMapper = new ExpertProfileRowMapper();

    // ✅ 저장 (INSERT)
    @Override
    public void save(ExpertProfileDTO expert) {
        String sql = "INSERT INTO expert_profile (member_id, career, university, certification, introduction, file_names, submitted_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        String fileNamesStr = (expert.getFileNames() != null)
                ? String.join(",", expert.getFileNames()) : null;

        jdbcTemplate.update(sql,
                expert.getMemberId(),
                expert.getCareer(),
                expert.getUniversity(),
                expert.getCertification(),
                expert.getIntroduction(),   // ✅ 추가
                fileNamesStr,
                Timestamp.valueOf(expert.getSubmittedAt())
        );
    }

    // ✅ member_id로 1건 조회
    @Override
    public Optional<ExpertProfileDTO> findByMemberId(String memberId) {
        String sql = "SELECT * FROM expert_profile WHERE member_id = ?";
        List<ExpertProfileDTO> results = jdbcTemplate.query(sql, rowMapper, memberId);
        return results.stream().findFirst();
    }

    // ✅ 전체 조회
    @Override
    public List<ExpertProfileDTO> findAll() {
        String sql = "SELECT * FROM expert_profile ORDER BY submitted_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // ❌ 승인 관련 메서드는 이제 필요 없음 → 삭제함

    // ✅ 삭제
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM expert_profile WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
