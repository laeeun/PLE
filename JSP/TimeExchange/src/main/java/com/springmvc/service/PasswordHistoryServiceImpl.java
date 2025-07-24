package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.PasswordHistoryDTO;
import com.springmvc.repository.PasswordHistoryRepository;

@Service
public class PasswordHistoryServiceImpl implements PasswordHistoryService{

	@Autowired
	private PasswordHistoryRepository passwordHistoryRepository;
	
	@Override
	public void save(PasswordHistoryDTO dto) {
		passwordHistoryRepository.save(dto);
	}

	@Override
	public List<PasswordHistoryDTO> findRecent3ByMemberId(String member_id) {
		return passwordHistoryRepository.findRecent3ByMemberId(member_id);
	}
	
}
