package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.HistoryDTO;

public interface HistoryService {
	
	void save(HistoryDTO history);
	
	List<HistoryDTO> findByMemberId(String member_id);
	
	HistoryDTO findById(Long historyId);

    boolean existsBySellerId(String sellerId);
    boolean existsByBuyerId(String buyerId);
}
