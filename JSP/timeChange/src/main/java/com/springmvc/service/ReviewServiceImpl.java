package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.HistoryDTO;
import com.springmvc.domain.ReviewDTO;
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
	public boolean existsByBuyerAndTalent(String buyerId, Long talentId) {
		return reviewRepository.existsByBuyerAndTalent(buyerId, talentId);
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
	
	
	
}
