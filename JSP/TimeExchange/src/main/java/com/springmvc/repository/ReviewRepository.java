package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.HistoryDTO;
import com.springmvc.domain.ReviewDTO;
import com.springmvc.domain.ReviewReplyDTO;

public interface ReviewRepository {

	
    void save(ReviewDTO review);
    void update(ReviewDTO review);
    void delete(Long id);
    ReviewDTO findById(Long id);
    boolean existsByHistoryId(Long history_id);
    Long findIdByHistoryId(Long historyId);
    Long findIdByBuyerAndTalent(String buyerId, Long talentId);
    HistoryDTO findHistoryId(Long History_id);
    List<ReviewDTO> findByWriterId(String writerId);
    List<ReviewDTO> findByTargetId(String targetId);
    void saveReply(ReviewReplyDTO reply);
    void updateReply(ReviewReplyDTO reply);
    void deleteReply(Long reviewId); // reviewId로 답글을 삭제
    
    //리뷰 리액션
    void saveReviewReaction(Long reviewId, String memberId, String reactionType);
    void deleteReviewReaction(Long reviewId, String memberId);
    String findReviewReaction(Long reviewId, String memberId); // → "LIKE", "DISLIKE", null
    int countReviewLikes(Long reviewId);
    int countReviewDislikes(Long reviewId);
    public List<ReviewDTO> findByTalentId(Long talentId);
    //리뷰 답글 리액션
    void saveReplyReaction(Long replyId, String memberId, String reactionType);
    void deleteReplyReaction(Long replyId, String memberId);
    String findReplyReaction(Long replyId, String memberId);
    int countReplyLikes(Long replyId);
    int countReplyDislikes(Long replyId);
    
    int countByTalentId(Long talentId);
    double getAverageRatingByTalentId(Long talentId);
    List<ReviewDTO> findByTalentIdPaged(Long talentId, int offset, int size);




}
