package com.springmvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springmvc.domain.PurchaseRequestDTO;

public interface purchaseService {
	
	// 구매 요청 저장
    public void save(PurchaseRequestDTO dto);

    // 특정 요청 ID로 상세 조회
    public PurchaseRequestDTO findById(Long requestId);

    // 특정 판매자가 받은 모든 요청 리스트
    public List<PurchaseRequestDTO> findBySeller(String sellerId);

    // 특정 구매자가 보낸 요청 리스트 (선택)
    public List<PurchaseRequestDTO> findByBuyer(String buyerId);

    // 요청 삭제 (요청 ID 기준)
    public void deleteById(Long requestId);
    //
    void updateStatus(Long requestId, String status);
    
    void updateAccountBalance(String memberId, int amount);
}
