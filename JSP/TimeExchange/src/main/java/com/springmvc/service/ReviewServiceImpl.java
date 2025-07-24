package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.HistoryDTO;
import com.springmvc.domain.ReviewDTO;
import com.springmvc.domain.ReviewReplyDTO;
import com.springmvc.repository.HistoryRepository;
import com.springmvc.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public void save(ReviewDTO review) {
		reviewRepository.save(review);
	}

	@Override
	public void update(ReviewDTO review) {
		reviewRepository.update(review);
	}

	@Override
	public void delete(Long id) {
		reviewRepository.delete(id);
	}

	@Override
	public ReviewDTO findById(Long id) {
		return reviewRepository.findById(id);
	}

	@Override
	public boolean existsByHistoryId(Long history_id) {
		return reviewRepository.existsByHistoryId(history_id);
	}
	
	@Override
	public Long findIdByHistoryId(Long historyId) {
	    return reviewRepository.findIdByHistoryId(historyId);
	}

	@Override
	public Long findIdByBuyerAndTalent(String buyerId, Long talentId) {
		return reviewRepository.findIdByBuyerAndTalent(buyerId, talentId);
	}

	@Override
	public HistoryDTO findHistoryId(Long History_id) {
		return reviewRepository.findHistoryId(History_id);
	}

	@Override
	public List<ReviewDTO> findByWriterId(String writerId) {
		return reviewRepository.findByWriterId(writerId);
	}
	
	@Override
	public List<ReviewDTO> findByTargetId(String targetId) {
	    return reviewRepository.findByTargetId(targetId);
	}

	@Override
	public void saveReply(ReviewReplyDTO reply) {
		reviewRepository.saveReply(reply);
	}

	@Override
	public void updateReply(ReviewReplyDTO reply) {
		reviewRepository.updateReply(reply);
	}

	@Override
	public void deleteReply(Long reviewId) {
		reviewRepository.deleteReply(reviewId);
	}

	@Override
	public void saveReviewReaction(Long reviewId, String memberId, String reactionType) {
		reviewRepository.saveReviewReaction(reviewId, memberId, reactionType);
	}

	@Override
	public void deleteReviewReaction(Long reviewId, String memberId) {
		reviewRepository.deleteReviewReaction(reviewId, memberId);
	}

	@Override
	public String findReviewReaction(Long reviewId, String memberId) {
		return reviewRepository.findReviewReaction(reviewId, memberId);
	}

	@Override
	public int countReviewLikes(Long reviewId) {
		return reviewRepository.countReviewLikes(reviewId);
	}

	@Override
	public int countReviewDislikes(Long reviewId) {
		return reviewRepository.countReviewDislikes(reviewId);
	}
	
	@Override
	public void saveReplyReaction(Long replyId, String memberId, String reactionType) {
		reviewRepository.saveReplyReaction(replyId, memberId, reactionType);
	}

	@Override
	public void deleteReplyReaction(Long replyId, String memberId) {
		reviewRepository.deleteReplyReaction(replyId, memberId);
	}

	@Override
	public String findReplyReaction(Long replyId, String memberId) {
		return reviewRepository.findReplyReaction(replyId, memberId);
	}

	@Override
	public int countReplyLikes(Long replyId) {
		return reviewRepository.countReplyLikes(replyId);
	}

	@Override
	public int countReplyDislikes(Long replyId) {
		return reviewRepository.countReplyDislikes(replyId);
	}

	@Override
	public int countByTalentId(Long talentId) {
	    return reviewRepository.countByTalentId(talentId);
	}

	@Override
	public double getAverageRatingByTalentId(Long talentId) {
	    return reviewRepository.getAverageRatingByTalentId(talentId);
	}

}
