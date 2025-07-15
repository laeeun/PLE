package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.HistoryDTO;
import com.springmvc.repository.HistoryRepository;

@Service
public class HistoryServiceImpl implements HistoryService{

	@Autowired
	private HistoryRepository historyRepository;
	
	@Override
	public void save(HistoryDTO history) {
		historyRepository.save(history);
	}

	@Override
	public List<HistoryDTO> findByMemberId(String member_id) {
		return historyRepository.findByMemberId(member_id);
	}
	
}
