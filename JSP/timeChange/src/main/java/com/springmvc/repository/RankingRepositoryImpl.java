package com.springmvc.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.RankingDTO;

@Repository
public class RankingRepositoryImpl implements RankingRepository {

    private final JdbcTemplate template;

    public RankingRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    // 랭킹 정보 최초 생성
    @Override
    public void saveRankingPoint(RankingDTO rankingDTO) {
        String sql = "INSERT INTO talent_ranking (talent_id, member_id, total_sales, score, created_at, updated_at) " +
                     "VALUES (?, ?, ?, ?, NOW(), NOW())";

        template.update(sql,
            rankingDTO.getTalent_id(),
            rankingDTO.getMember_id(),
            rankingDTO.getTotal_sales(),
            rankingDTO.getScore()
        );
    }

    // 기존 랭킹 정보 갱신 (점수, 판매수)
    @Override
    public void updateRankingPoint(RankingDTO rankingDTO) {
        String sql = "UPDATE talent_ranking " +
                     "SET total_sales = ?, score = ?, updated_at = NOW() " +
                     "WHERE talent_id = ?";

        template.update(sql,
            rankingDTO.getTotal_sales(),
            rankingDTO.getScore(),
            rankingDTO.getTalent_id()
        );
    }

    // 전체 랭킹 Top 10 조회
    @Override
    public List<RankingDTO> getTopRankingList() {
        String sql = "SELECT r.*, t.title, m.username " +
                     "FROM talent_ranking r " +
                     "JOIN talent t ON r.talent_id = t.talent_id " +
                     "JOIN member m ON r.member_id = m.member_id " +
                     "ORDER BY r.score DESC " +
                     "LIMIT 10";

        return template.query(sql, new RankingRowMapper());
    }

    // 카테고리별 랭킹 Top 10 조회
    @Override
    public List<RankingDTO> getTopRankingListByCategory(String category) {
        String sql = "SELECT r.*, t.title, t.category, m.username " +
                     "FROM talent_ranking r " +
                     "JOIN talent t ON r.talent_id = t.talent_id " +
                     "JOIN member m ON r.member_id = m.member_id " +
                     "WHERE t.category = ? " +
                     "ORDER BY r.score DESC " +
                     "LIMIT 10";

        return template.query(sql, new RankingRowMapper(), category);
    }

    // 재능 ID로 랭킹 단건 조회
    @Override
    public RankingDTO findByTalentId(long talentId) {
        String sql = "SELECT r.*, t.title, m.username " +
                     "FROM talent_ranking r " +
                     "JOIN talent t ON r.talent_id = t.talent_id " +
                     "JOIN member m ON r.member_id = m.member_id " +
                     "WHERE r.talent_id = ?";

        return template.queryForObject(sql, new RankingRowMapper(), talentId);
    }

    // 해당 재능의 랭킹 존재 여부 확인
    @Override
    public boolean existsByTalentId(long talentId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM talent_ranking WHERE talent_id = ?)";
        return template.queryForObject(sql, Boolean.class, talentId);	
    }

    // 특정 회원의 전체 랭킹 목록 조회
    @Override
    public List<RankingDTO> findByMemberId(String memberId) {
        String sql = "SELECT r.*, t.title, t.category, m.username " +
                     "FROM talent_ranking r " +
                     "JOIN talent t ON r.talent_id = t.talent_id " +
                     "JOIN member m ON r.member_id = m.member_id " +
                     "WHERE r.member_id = ? " +
                     "ORDER BY r.updated_at DESC";

        return template.query(sql, new RankingRowMapper(), memberId);
    }

}
