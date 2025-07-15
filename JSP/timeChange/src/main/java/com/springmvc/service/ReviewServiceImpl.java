package com.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.ReviewDTO;
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
	
}
