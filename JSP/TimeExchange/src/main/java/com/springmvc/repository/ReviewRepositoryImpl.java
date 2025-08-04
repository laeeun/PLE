package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.controller.ChargeController;
import com.springmvc.domain.HistoryDTO;
import com.springmvc.domain.ReviewDTO;
import com.springmvc.domain.ReviewReplyDTO;

@Repository  // Spring이 이 클래스를 DAO(데이터베이스 처리 클래스)로 인식하게 함
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ChargeController chargeController;

    @Autowired
    private JdbcTemplate template;

    ReviewRepositoryImpl(ChargeController chargeController) {
        this.chargeController = chargeController;
    } // Spring에서 제공하는 SQL 실행 도구 (JDBC 쉽게 처리)

    /**
     * 리뷰 등록 (CREATE)
     * DB에 새 리뷰를 삽입
     */
    @Override
    public void save(ReviewDTO review) {
        String sql = "INSERT INTO review (history_id, writer_name, target_name, " +
                     "talent_id, rating, comment, category) VALUES (?, ?, ?, ?, ?, ?, ?)";

        template.update(sql,
            review.getHistoryId(),   
            review.getWriterName(),    
            review.getTargetName(),   
            review.getTalentId(),
            review.getRating(),
            review.getComment(),
            review.getCategory());
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
    	String sql = "SELECT r.*, " +
                "rr.reply_id, rr.seller_id AS seller_id, " +
                "rr.reply_content AS content, " +
                "r.created_at AS created_at, " +
                "rr.reply_created_at AS reply_created_at " +
                "FROM review r " +
                "LEFT JOIN review_reply rr ON r.review_id = rr.review_id " +
                "WHERE r.review_id = ?";

        ReviewDTO review = template.queryForObject(sql, new ReviewRowMapper(), id);

        // ⭐ 좋아요, 싫어요 카운트 추가
        int likeCount = countReviewLikes(id);
        int dislikeCount = countReviewDislikes(id);

        review.setLikeCount(likeCount);
        review.setDislikeCount(dislikeCount);

        return review;
    }



    /**
     * 리뷰 작성 여부 확인
     * 구매자 ID와 재능 ID를 기준으로 해당 리뷰가 DB에 존재하는지 확인
     */
    @Override
    public boolean existsByHistoryId(Long history_id) {
        String sql = "SELECT COUNT(*) FROM review WHERE history_id = ?";
        Integer count = template.queryForObject(sql, Integer.class, history_id);
        return count != null && count > 0;
    }

    
    @Override
    public Long findIdByHistoryId(Long historyId) {
        String sql = "SELECT review_id FROM review WHERE history_id = ?";
        return template.queryForObject(sql, Long.class, historyId);
    }

    /**
     * 이미 작성된 리뷰의 ID 조회
     * 구매자 ID와 재능 ID로 해당 리뷰의 고유 ID를 가져옴
     */
    @Override
    public Long findIdByBuyerAndTalent(String buyerId, Long talentId) {
        String sql = "SELECT review_id FROM review WHERE writer_name = ? AND talent_id = ? LIMIT 1";
        return template.queryForObject(sql, Long.class, buyerId, talentId);
    }

	@Override
	public HistoryDTO findHistoryId(Long history_id) {
		String sql = "SELECT * FROM history WHERE history_id = ?";
		return template.queryForObject(sql, new HistoryRowMapper(), history_id);
	}

	@Override
	public List<ReviewDTO> findByWriterId(String writerName) {
		String sql = "SELECT r.review_id, r.writer_name, r.target_name, r.talent_id, " +
	             "r.rating, r.comment, r.created_at AS created_at, r.history_id, r.category, " +
	             "rr.reply_id, rr.seller_id AS seller_id, " +
	             "rr.reply_content AS content, rr.reply_created_at AS reply_created_at " +
	             "FROM review r " +
	             "LEFT JOIN review_reply rr ON r.review_id = rr.review_id " +
	             "WHERE r.writer_name = ?";


	    List<ReviewDTO> list = template.query(sql, new ReviewRowMapper(), writerName);

	   
	    for (ReviewDTO review : list) {
	        review.setLikeCount(countReviewLikes(review.getReviewId()));
	        review.setDislikeCount(countReviewDislikes(review.getReviewId()));
	    }

	    return list;
	}

 
	@Override
	public List<ReviewDTO> findByTargetId(String targetName) {
		String sql = "SELECT r.review_id, r.writer_name, r.target_name, r.talent_id, " +
	             "r.rating, r.comment, r.created_at AS created_at, r.history_id, r.category, " +
	             "rr.reply_id, rr.seller_id AS seller_id, " +
	             "rr.reply_content AS content, rr.reply_created_at AS reply_created_at " +
	             "FROM review r " +
	             "LEFT JOIN review_reply rr ON r.review_id = rr.review_id " +
	             "WHERE r.target_name = ?";

	    List<ReviewDTO> list = template.query(sql, new ReviewRowMapper(), targetName);

	   
	    for (ReviewDTO review : list) {
	        review.setLikeCount(countReviewLikes(review.getReviewId()));
	        review.setDislikeCount(countReviewDislikes(review.getReviewId()));
	    }

	    return list;
	}


	@Override
	public void saveReply(ReviewReplyDTO reply) {
		String sql = "INSERT INTO review_reply (review_id, seller_id, reply_content) VALUES (?, ?, ?)";
		template.update(sql, reply.getReviewId(), reply.getSellerId(), reply.getReplyContent());
	}

	@Override
	public void updateReply(ReviewReplyDTO reply) {
		String sql = "UPDATE review_reply SET reply_content = ? WHERE reply_id = ?";
		template.update(sql, reply.getReplyContent(), reply.getReplyId());
	}

	@Override
	public void deleteReply(Long reviewId) {
		String sql = "DELETE FROM review_reply WHERE review_id = ?";
		template.update(sql, reviewId);
	}

	@Override
	public void saveReviewReaction(Long reviewId, String memberId, String reactionType) {
		
		System.out.println("🔥 저장 직전 reactionType = " + reactionType);
		 
		String sql = "INSERT INTO review_reaction (review_id, member_id, reaction_type) " +
		             "VALUES (?, ?, ?) " +
		             "ON DUPLICATE KEY UPDATE reaction_type = ?";
		template.update(sql, reviewId, memberId, reactionType, reactionType);
	}

	@Override
	public void deleteReviewReaction(Long reviewId, String memberId) {
		String sql = "DELETE FROM review_reaction WHERE review_id = ? AND member_id = ?";
		template.update(sql, reviewId, memberId);
	}

	@Override
	public String findReviewReaction(Long reviewId, String memberId) {
		String sql = "SELECT reaction_type FROM review_reaction WHERE review_id = ? AND member_id = ?";
		List<String> result = template.query(sql, (rs, rownum) -> rs.getString("reaction_type"), reviewId, memberId);
		return result.isEmpty() ? null : result.get(0);
	}

    @Override
    public int countReviewLikes(Long reviewId) {
        String sql = "SELECT COUNT(*) FROM review_reaction WHERE review_id = ? AND reaction_type = 'LIKE'";
        return template.queryForObject(sql, Integer.class, reviewId);
    }

    @Override
    public int countReviewDislikes(Long reviewId) {
        String sql = "SELECT COUNT(*) FROM review_reaction WHERE review_id = ? AND reaction_type = 'DISLIKE'";
        return template.queryForObject(sql, Integer.class, reviewId);
    }

    @Override
    public void saveReplyReaction(Long replyId, String memberId, String reactionType) {
        String sql = "INSERT INTO review_reply_reaction (reply_id, member_id, reaction_type) VALUES (?, ?, ?)";
        template.update(sql, replyId, memberId, reactionType);
    }

    @Override
    public void deleteReplyReaction(Long replyId, String memberId) {
        String sql = "DELETE FROM review_reply_reaction WHERE reply_id = ? AND member_id = ?";
        template.update(sql, replyId, memberId);
    }

    @Override
    public String findReplyReaction(Long replyId, String memberId) {
        String sql = "SELECT reaction_type FROM review_reply_reaction WHERE reply_id = ? AND member_id = ?";
        List<String> result = template.query(sql,
            (rs, rowNum) -> rs.getString("reaction_type"),
            replyId, memberId);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public int countReplyLikes(Long replyId) {
        String sql = "SELECT COUNT(*) FROM review_reply_reaction WHERE reply_id = ? AND reaction_type = 'LIKE'";
        return template.queryForObject(sql, Integer.class, replyId);
    }

    @Override
    public int countReplyDislikes(Long replyId) {
        String sql = "SELECT COUNT(*) FROM review_reply_reaction WHERE reply_id = ? AND reaction_type = 'DISLIKE'";
        return template.queryForObject(sql, Integer.class, replyId);
    }

    @Override
    public int countByTalentId(Long talentId) {
        String sql = "SELECT COUNT(*) FROM review WHERE talent_id = ?";
        return template.queryForObject(sql, Integer.class, talentId);
    }


    @Override
    public double getAverageRatingByTalentId(Long talentId) {
        String sql = "SELECT IFNULL(AVG(rating), 0) FROM review WHERE talent_id = ?";
        return template.queryForObject(sql, Double.class, talentId);
    }

	@Override
	public List<ReviewDTO> findByTalentId(Long talentId) {
		String sql = """
				SELECT r.review_id, r.writer_name, r.target_name, r.talent_id,
				       r.rating, r.comment, r.created_at AS created_at,
				       r.history_id, r.category,
				       rr.reply_id, rr.seller_id,
				       rr.reply_content AS content,
				       rr.reply_created_at AS reply_created_at
				FROM review r
				LEFT JOIN review_reply rr ON r.review_id = rr.review_id
				WHERE r.talent_id = ?
				ORDER BY r.created_at DESC
			""";

		return template.query(sql, new ReviewRowMapper(), talentId);
	}

	@Override
	public List<ReviewDTO> findByTalentIdPaged(Long talentId, int offset, int size) {
	    String sql = """
	        SELECT *
	        FROM review r
	        LEFT JOIN review_reply rr ON r.review_id = rr.review_id
	        WHERE r.talent_id = ?
	        ORDER BY r.created_at DESC
	        LIMIT ? OFFSET ?
	    """;
	    return template.query(sql, new ReviewRowMapper(), talentId, size, offset);
	}
	
	
    
    
    
}
