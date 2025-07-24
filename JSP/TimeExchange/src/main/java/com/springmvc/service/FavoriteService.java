package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.FavoriteDTO;
import com.springmvc.domain.Member;

public interface FavoriteService {

    /** 
     * 찜 추가 
     * - 사용자가 재능을 찜할 때 호출
     */
    void saveFavoriteTalent(FavoriteDTO favorite);

    /** 
     * 찜 삭제 (memberId + talentId 기반) 
     * - 사용자가 찜을 해제할 때
     */
    void deleteFavoriteTalent(String memberId, long talentId);

    /** 
     * 찜 여부 확인 
     * - 재능 상세보기에서 하트 상태를 판단할 때 사용
     */
    boolean exists(String memberId, long talentId);

    /** 
     * 찜 토글 기능 (추가 제안) 
     * - 찜했으면 삭제, 안 했으면 추가 (UI 토글 대응용)
     */
    boolean toggleFavorite(String memberId, long talentId); 
    
    // ✅ 새로 추가
    List<FavoriteDTO> readPagedFavoriteList(String memberId, int offset, int size);

    int getFavoriteCount(String memberId);
    
   
}
