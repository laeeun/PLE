package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.RankingDTO;

public interface RankingService {

    // 랭킹 최초 생성 (첫 판매 시)
    void saveRankingPoint(RankingDTO rankingDTO);

    // 랭킹 점수 갱신 (추가 판매 시)
    void updateRankingPoint(RankingDTO rankingDTO);

    // 전체 랭킹 Top 10 조회
    List<RankingDTO> getTopRankingList();

    // 카테고리별 랭킹 Top 10 조회
    List<RankingDTO> getTopRankingListByCategory(String category);

    // 재능 ID로 단일 랭킹 정보 조회
    RankingDTO findByTalentId(long talentId);

    // 해당 재능의 랭킹 존재 여부 확인
    boolean existsByTalentId(long talentId);

    // 회원 ID로 재능 랭킹 목록 조회 (마이페이지용)
    List<RankingDTO> findByMemberId(String memberId);
    
    public void handleRankingAfterPurchase(long talentId, String memberId);
    
    public double calculateScore(int totalSales);
}
