package com.springmvc.service;

import java.util.List;
import com.springmvc.domain.Member;

public interface MemberService {

    // ✅ [기본 CRUD 기능] --------------------------------------

    // 회원 등록 (회원가입)
    void save(Member member);

    // 회원 ID로 단일 회원 조회
    Member findById(String member_id);

    // 전체 회원 목록 조회
    List<Member> findAll();

    // 회원 정보 수정
    void update(Member member);

    // 회원 삭제 (회원 탈퇴 등)
    void delete(String member_id);

    // ID 중복 여부 확인 (중복이면 true 반환)
    boolean isDuplicateId(String member_id);

    // username 중복 여부 확인 (중복이면 true 반환)
    boolean isDuplicateUsername(String username);


    // ✅ [확장 기능 - 로그인/찾기/관리자 기능] ------------------

    // 로그인 처리 (member_id로 회원 조회 → 비밀번호는 Controller나 Security에서 비교)
    Member login(String member_id);

    // 관리자 계정 목록 조회 (is_admin 컬럼이 있는 경우 사용 예정)
    List<Member> findAdmins();

    // 이름 + 전화번호로 아이디 찾기
    String findId(String name, String phone);

    // 회원 비밀번호 변경 (암호화된 비밀번호 저장)
    void updatePassword(String member_id, String encodedPw);

    // 이름 + 전화번호로 member_id와 created_at 함께 조회 (추후 DTO로 확장 가능)
    Member findIdWithCreatedAt(String name, String phone);

    // 이름 + 아이디 + 이메일로 회원 조회 (비밀번호 찾기 단계에서 본인 인증)
    Member findByNameIdEmail(String name, String member_id, String email);
}
