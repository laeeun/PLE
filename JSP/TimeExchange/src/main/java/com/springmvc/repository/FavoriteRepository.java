package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.FavoriteDTO;
import com.springmvc.domain.Member;

public interface FavoriteRepository {

    /** 찜 추가 */
    void saveFavoriteTalent(FavoriteDTO favorite);

    
    /** 찜 삭제 (memberId + talentId 기반) */
    void deleteFavoriteTalent(String memberId, long talentId);

    /** 찜 여부 확인 */
    boolean exists(String memberId, long talentId);
    
    List<FavoriteDTO> readPagedFavoriteList(String memberId, int offset, int size);
    
    int getFavoriteCount(String memberId);
}

