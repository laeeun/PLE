package com.springmvc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.FavoriteDTO;
import com.springmvc.domain.Member;
import com.springmvc.repository.FavoriteRepository;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /** 찜 추가 */
    @Override
    public void saveFavoriteTalent(FavoriteDTO favorite) {
        favoriteRepository.saveFavoriteTalent(favorite);
    }

    /** 찜 목록 조회 */
    
      
    @Override
	public List<FavoriteDTO> readPagedFavoriteList(String memberId, int offset, int size) {
    	return favoriteRepository.readPagedFavoriteList(memberId, offset, size);
	}

	@Override
	public int getFavoriteCount(String memberId) {
		return favoriteRepository.getFavoriteCount(memberId);
	}

	/** 찜 해제 */
    @Override
    public void deleteFavoriteTalent(String memberId, long talentId) {
        favoriteRepository.deleteFavoriteTalent(memberId, talentId);
    }

    /** 찜 여부 확인 */
    @Override
    public boolean exists(String memberId, long talentId) {
        return favoriteRepository.exists(memberId, talentId);
    }
    
   

	/** 찜 토글: 이미 찜했으면 삭제, 안 했으면 추가 */
    @Override
    public boolean toggleFavorite(String memberId, long talentId) {
        if (exists(memberId, talentId)) {
            deleteFavoriteTalent(memberId, talentId);
            return false; // 해제됨
        } else {
            FavoriteDTO favorite = new FavoriteDTO();
            favorite.setMemberId(memberId);
            favorite.setTalentId(talentId);
            favorite.setCreated_at(LocalDateTime.now());
            saveFavoriteTalent(favorite);
            return true; // 추가됨
        }
    }
}
