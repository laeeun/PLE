package com.springmvc.service;

import com.springmvc.domain.ReviewDTO;

public interface ReviewService{
	void save(ReviewDTO review);
    void update(ReviewDTO review);
    void delete(Long id);
    ReviewDTO findById(Long id);
    boolean existsByBuyerAndTalent(String buyerId, Long talentId);
    Long findIdByBuyerAndTalent(String buyerId, Long talentId);
}
