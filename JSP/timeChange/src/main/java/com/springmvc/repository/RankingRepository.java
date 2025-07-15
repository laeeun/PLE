package com.springmvc.repository;

import java.util.List;
import com.springmvc.domain.RankingDTO;

public interface RankingRepository {

    /**
     * 랭킹 최초 생성
     * - 재능이 처음 판매되었을 때 insert 용도
     */
    void saveRankingPoint(RankingDTO rankingDTO);

    /**
     * 랭킹 점수 갱신
     * - 판매 발생 시 기존 점수 업데이트
     */
    void updateRankingPoint(RankingDTO rankingDTO);

    /**
     * 전체 랭킹 목록 조회 (상위 10개)
     * - 메인화면 또는 랭킹 페이지에서 TOP10 노출용
     */
    List<RankingDTO> getTopRankingList();

    /**
     * 카테고리별 랭킹 목록 조회 (상위 10개)
     * - 카테고리별 TOP10 랭킹 노출
     */
    List<RankingDTO> getTopRankingListByCategory(String category);

    /**
     * 해당 재능의 랭킹 정보 단건 조회
     * - update 또는 마이페이지용 내부 로직 처리
     */
    RankingDTO findByTalentId(long talentId);

    /**
     * 해당 재능의 랭킹 존재 여부 확인
     * - 존재하면 update / 없으면 save 처리 구분용
     */
    boolean existsByTalentId(long talentId);

    /**
     * 특정 회원이 등록한 재능들의 랭킹 정보 목록 조회
     * - 마이페이지에서 "내 랭킹 보기"용
     */
    List<RankingDTO> findByMemberId(String memberId);
}
