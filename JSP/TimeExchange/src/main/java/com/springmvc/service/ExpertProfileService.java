package com.springmvc.service;

import com.springmvc.domain.ExpertProfileDTO;

import java.util.List;
import java.util.Optional;

public interface ExpertProfileService {

    // 전문가 정보 저장
    void save(ExpertProfileDTO expert);

    // 회원 ID로 조회
    Optional<ExpertProfileDTO> findByMemberId(String memberId);

    // 전체 전문가 목록
    List<ExpertProfileDTO> findAll();

    // 삭제
    void delete(Long id);
}
