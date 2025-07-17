package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.HistoryDTO;
import com.springmvc.domain.ReviewDTO;

public interface ReviewRepository {

	
    void save(ReviewDTO review);
    void update(ReviewDTO review);
    void delete(Long id);
    ReviewDTO findById(Long id);
    boolean existsByBuyerAndTalent(String buyerId, Long talentId);
    Long findIdByBuyerAndTalent(String buyerId, Long talentId);
    HistoryDTO findHistoryId(Long History_id);
    List<ReviewDTO> findByWriterId(String writerId);
    
}
