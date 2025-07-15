package com.springmvc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.ReviewDTO;

@Repository  // Spring이 이 클래스를 DAO(데이터베이스 처리 클래스)로 인식하게 함
public class ReviewRepositoryImpl implements ReviewRepository {

    @Autowired
    private JdbcTemplate template; // Spring에서 제공하는 SQL 실행 도구 (JDBC 쉽게 처리)

    /**
     * 리뷰 등록 (CREATE)
     * DB에 새 리뷰를 삽입
     */
    @Override
    public void save(ReviewDTO review) {
        String sql = "INSERT INTO review (writer_id, target_id, talent_id, rating, comment) VALUES (?, ?, ?, ?, ?)";
        template.update(sql, 
            review.getWriterId(), 
            review.getTargetId(), 
            review.getTalentId(),
            review.getRating(), 
            review.getComment());
    }

    /**
     * 리뷰 수정 (UPDATE)
     * 주어진 review_id에 해당하는 리뷰의 평점, 댓글을 수정
     */
    @Override
    public void update(ReviewDTO review) {
        String sql = "UPDATE review SET rating = ?, comment = ? WHERE review_id = ?";
        template.update(sql, 
            review.getRating(), 
            review.getComment(), 
            review.getReviewId());
    }

    /**
     * 리뷰 삭제 (DELETE)
     * review_id로 특정 리뷰 삭제
     */
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM review WHERE review_id = ?";
        template.update(sql, id);
    }

    /**
     * 리뷰 상세 조회 (READ)
     * review_id에 해당하는 리뷰 1개 조회
     */
    @Override
    public ReviewDTO findById(Long id) {
        String sql = "SELECT * FROM review WHERE review_id = ?";
        return template.queryForObject(sql, new ReviewRowMapper(), id); // RowMapper로 결과 → DTO 변환
    }

    /**
     * 리뷰 작성 여부 확인
     * 구매자 ID와 재능 ID를 기준으로 해당 리뷰가 DB에 존재하는지 확인
     */
    @Override
    public boolean existsByBuyerAndTalent(String buyerId, Long talentId) {
        String sql = "SELECT COUNT(*) FROM review WHERE writer_id = ? AND talent_id = ?";
        Integer count = template.queryForObject(sql, Integer.class, buyerId, talentId);
        return count != null && count > 0; // 1개 이상이면 true
    }

    /**
     * 이미 작성된 리뷰의 ID 조회
     * 구매자 ID와 재능 ID로 해당 리뷰의 고유 ID를 가져옴
     */
    @Override
    public Long findIdByBuyerAndTalent(String buyerId, Long talentId) {
        String sql = "SELECT review_id FROM review WHERE writer_id = ? AND talent_id = ? LIMIT 1";
        return template.queryForObject(sql, Long.class, buyerId, talentId);
    }  
}
