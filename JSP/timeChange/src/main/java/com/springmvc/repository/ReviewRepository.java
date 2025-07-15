package com.springmvc.repository;

import com.springmvc.domain.ReviewDTO;

import com.springmvc.domain.ReviewDTO;

import com.springmvc.domain.ReviewDTO;

public interface ReviewRepository {
    void save(ReviewDTO review);
    void update(ReviewDTO review);
    void delete(Long id);
    ReviewDTO findById(Long id);
    boolean existsByBuyerAndTalent(String buyerId, Long talentId);
    Long findIdByBuyerAndTalent(String buyerId, Long talentId);
}
