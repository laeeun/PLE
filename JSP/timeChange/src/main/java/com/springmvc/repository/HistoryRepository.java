package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.History;

public interface HistoryRepository {
	
	void save(History history);
	
	List<History> findByMemberId(String member_id);
}
