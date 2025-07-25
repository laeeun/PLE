package com.springmvc.repository;

import com.springmvc.domain.ExpertProfileDTO;
import java.util.List;
import java.util.Optional;

public interface ExpertProfileRepository {

    // ✅ 전문가 정보 저장 (INSERT)
    void save(ExpertProfileDTO expert);

    // ✅ 회원 ID로 전문가 정보 조회 (1:1 관계라 Optional)
    Optional<ExpertProfileDTO> findByMemberId(String memberId);

    // ✅ 모든 전문가 조회 (관리자 페이지용)
    List<ExpertProfileDTO> findAll();

    // ✅ 삭제
    void deleteById(Long id);
}
