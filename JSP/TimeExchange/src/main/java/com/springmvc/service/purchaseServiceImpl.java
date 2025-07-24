package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.PurchaseRequestDTO;
import com.springmvc.repository.purchaseRepository;

@Service
public class purchaseServiceImpl implements purchaseService{
	@Autowired
	private purchaseRepository purchaseRepository;
	@Override
	public void save(PurchaseRequestDTO dto) {
		purchaseRepository.save(dto);
	}

	@Override
	public PurchaseRequestDTO findById(Long requestId) {
		return purchaseRepository.findById(requestId);
	}

	@Override
	public List<PurchaseRequestDTO> findBySeller(String sellerId) {
		return purchaseRepository.findBySeller(sellerId);
	}

	@Override
	public List<PurchaseRequestDTO> findByBuyer(String buyerId) {
		return purchaseRepository.findByBuyer(buyerId);
	}

	@Override
	public void deleteById(Long requestId) {
		purchaseRepository.deleteById(requestId);	
	}

	@Override
	public void updateStatus(Long requestId, String status) {
		purchaseRepository.updateStatus(requestId, status);	
	}

	@Override
	public void updateAccountBalance(String memberId, int amount) {
		purchaseRepository.updateAccountBalance(memberId, amount);	
	}
	
	
}
