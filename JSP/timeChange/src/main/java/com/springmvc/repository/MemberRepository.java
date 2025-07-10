package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.Member;

// member 관련 데이터 접근을 정의하는 인터페이스 (DAO 역할)
public interface MemberRepository {

	
	// 기본 CRUD
    void save(Member member);                      // 회원 저장
    
    Member findById(String member_id);                    // ID로 회원 조회
    
    List<Member> findAll();                        // 전체 회원 조회
    
    void update(Member member);                    // 회원 정보 수정
    
    void delete(String member_id);                        // 회원 삭제
    
    Member findByUsername(String username);

    
    // 확장 기능
    Member login(String member_id);      // 로그인 처리
    
    Member findByEmail(String email);              // 이메일로 회원 찾기
    
    
    List<Member> findAdmins();                     // 관리자 목록
    
    
    Member findIdWithCreatedAt(String name, String phone);
    
    Member findByNameIdEmail(String name, String member_id, String email);
	
}
