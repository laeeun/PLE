package com.springmvc.repository;

import java.util.List;
import com.springmvc.domain.MemberDTO;

// ✅ member 테이블 관련 DB 접근 로직을 정의하는 인터페이스 (DAO 역할)
public interface MemberRepository {

    // ✅ [기본 CRUD 기능] -----------------------------

    // 회원 저장 (회원가입 시 사용)
    void save(MemberDTO member);

    // ID(member_id)로 회원 1명 조회
    MemberDTO findById(String member_id);

    // 전체 회원 목록 조회
    List<MemberDTO> findAll();

    // 회원 정보 수정
    void update(MemberDTO member);

    // 회원 삭제 (회원 탈퇴)
    void delete(String member_id);

    // username으로 회원 조회 (중복 체크 or 로그인용)
    MemberDTO findByUsername(String username);


    // ✅ [확장 기능] -----------------------------

    // 로그인 처리 (ID로 회원을 불러오고, 비밀번호는 서비스나 Security에서 비교)
    MemberDTO login(String member_id);

    // 관리자 회원들만 조회 (is_admin 필드 등 기준으로 분리)
    List<MemberDTO> findAdmins();

    // 이름 + 전화번호로 아이디 조회 (아이디 찾기 기능)
    String findId(String name, String phone);

    // 비밀번호 변경 (새로운 비밀번호를 암호화해서 저장)
    void updatePassword(String member_id, String encodedPw);

    // 이름 + 전화번호로 member_id와 생성일(created_at)까지 조회 (확장 가능)
    MemberDTO findIdWithCreatedAt(String name, String phone);

    // 이름 + 아이디 + 이메일로 회원 정보 조회 (비밀번호 재설정 시 본인 인증용)
    MemberDTO findByNameIdEmail(String name, String member_id, String email);
}
