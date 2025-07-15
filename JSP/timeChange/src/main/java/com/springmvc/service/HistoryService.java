package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.History;

public interface HistoryService {
	
	void save(History history);
	
	List<History> findByMemberId(String member_id);
}
