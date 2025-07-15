package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.RankingDTO;
import com.springmvc.repository.RankingRepository;

@Service
public class RankingServiceImpl implements RankingService {

    @Autowired
    RankingRepository rankingRepository;

    // 랭킹 최초 저장
    @Override
    public void saveRankingPoint(RankingDTO rankingDTO) {
        rankingRepository.saveRankingPoint(rankingDTO);
    }

    // 랭킹 점수 업데이트
    @Override
    public void updateRankingPoint(RankingDTO rankingDTO) {
        rankingRepository.updateRankingPoint(rankingDTO);
    }

    // 전체 랭킹 Top 10 조회
    @Override
    public List<RankingDTO> getTopRankingList() {
        return rankingRepository.getTopRankingList();
    }

    // 카테고리별 랭킹 Top 10 조회
    @Override
    public List<RankingDTO> getTopRankingListByCategory(String category) {
        return rankingRepository.getTopRankingListByCategory(category);
    }

    // 재능 ID로 단건 랭킹 조회
    @Override
    public RankingDTO findByTalentId(long talentId) {
        return rankingRepository.findByTalentId(talentId);
    }

    // 해당 재능의 랭킹 존재 여부 확인
    @Override
    public boolean existsByTalentId(long talentId) {
        return rankingRepository.existsByTalentId(talentId);
    }

    // 회원 ID로 등록한 재능의 랭킹 목록 조회
    @Override
    public List<RankingDTO> findByMemberId(String memberId) {
        return rankingRepository.findByMemberId(memberId);
    }
    
    @Override
	public void handleRankingAfterPurchase(long talentId, String memberId) {
    	if (rankingRepository.existsByTalentId(talentId)) {
            // 기존 랭킹이 존재할 경우 → 누적 갱신
            RankingDTO existing = rankingRepository.findByTalentId(talentId);
            int updatedSales = existing.getTotal_sales() + 1;
            double updatedScore = calculateScore(updatedSales);

            existing.setTotal_sales(updatedSales);
            existing.setScore(updatedScore);

            rankingRepository.updateRankingPoint(existing);
        } else {
            // 최초 판매일 경우 → 새로 insert
            RankingDTO newRanking = new RankingDTO();
            newRanking.setTalent_id(talentId);
            newRanking.setMember_id(memberId);
            newRanking.setTotal_sales(1);
            newRanking.setScore(calculateScore(1));

            rankingRepository.saveRankingPoint(newRanking);
        }
		
	}
    
    @Override
    public double calculateScore(int totalSales) {
        return totalSales * 10.0; // 판매 1건당 10점
    }
    
}
