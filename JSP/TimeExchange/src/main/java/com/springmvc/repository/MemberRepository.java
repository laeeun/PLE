package com.springmvc.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springmvc.domain.Member;

// ✅ member 테이블 관련 DB 접근 로직을 정의하는 인터페이스 (DAO 역할)
public interface MemberRepository {

    // ✅ [기본 CRUD 기능] -----------------------------

    // 회원 저장 (회원가입 시 사용)
    void save(Member member);

    // ID(member_id)로 회원 1명 조회
    Member findById(String member_id);

    // 전체 회원 목록 조회
    List<Member> findAll();

    // 회원 정보 수정
    void update(Member member);

    // 회원 삭제 (회원 탈퇴)
    void delete(String member_id);

    // username으로 회원 조회 (중복 체크 or 로그인용)
    Member findByUsername(String username);
    
    boolean existsByEmail(String email);
    
    


    // ✅ [확장 기능] -----------------------------

    // 로그인 처리 (ID로 회원을 불러오고, 비밀번호는 서비스나 Security에서 비교)
    Member login(String member_id);

    // 관리자 회원들만 조회 (is_admin 필드 등 기준으로 분리)
    List<Member> findAdmins();

    // 이름 + 전화번호로 아이디 조회 (아이디 찾기 기능)
    String findId(String name, String phone);

    // 비밀번호 변경 (새로운 비밀번호를 암호화해서 저장)
    void updatePassword(String member_id, String encodedPw);

    // 이름 + 전화번호로 member_id와 생성일(created_at)까지 조회 (확장 가능)
    Member findIdWithCreatedAt(String name, String phone);

    // 이름 + 아이디 + 이메일로 회원 정보 조회 (비밀번호 재설정 시 본인 인증용)
    Member findByNameIdEmail(String name, String member_id, String email);
    
    // 이메일 인증 토큰으로 회원 조회
    Member findByEmailToken(String token);
    
    // 이메일 인증 완료 처리 (email_verified = true 로 변경)
    void verifyEmail(String member_id);
    
    // 휴대폰 번호로 회원 조회
    Member findByPhone(String phone);

    // 인증번호, 생성시간 저장 (phone_cert_code, phone_cert_created_at)
    void updatePhoneCertInfo(String phone, String code, LocalDateTime createdAt);

    // 인증 성공 처리 (phone_verified = true)
    void updatePhoneVerified(String phone);

    void updateLoginFailInfo(Member member);
    
    // 프로필 이미지 파일명 업데이트
    void updateProfileImage(String member_id, String profileImage);
    
    public void updatePasswordWithTemp(String member_id, String encodedPw, String tempPw);
    
    void updateAccountBalance(String memberId, int amount);  
    
    boolean isDuplicateId(String member_id); 
    
    boolean existsByUsernameExceptMe(String username, String myId);
}
