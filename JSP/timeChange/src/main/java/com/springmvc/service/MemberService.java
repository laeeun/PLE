package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.Member;

public interface MemberService {
	
	// 기본 CRUD
    void save(Member member);                      // 회원 저장
    
    Member findById(String memberId);                    // ID로 회원 조회
    
    List<Member> findAll();                        // 전체 회원 조회
    
    void update(Member member);                    // 회원 정보 수정
    
    void delete(String memberId);                        // 회원 삭제

    // 확장 기능
    Member login(String memberId, String password);      // 로그인 처리
    
    Member findByEmail(String email);              // 이메일로 회원 찾기
    
    String findId(String name, String phone);      // 이름 + 전화번호로 ID 찾기
    
    List<Member> findAdmins();                     // 관리자 목록
    
}
