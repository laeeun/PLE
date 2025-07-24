package com.springmvc.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springmvc.domain.Member;
import com.springmvc.domain.MemberStatus;
import com.springmvc.repository.MemberRepository;
import com.springmvc.repository.purchaseRepository;


@Service // ✅ 이 클래스는 비즈니스 로직을 처리하는 서비스 계층으로, 스프링이 관리하는 Bean이다
public class MemberServiceImpl implements MemberService {


    @Autowired
    private MemberRepository memberRepository; // ✅ DB와 연결된 DAO 계층 (Repository) 주입
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    purchaseRepository purchaseRepository;

    // ✅ 회원 등록 (가입)
    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }

    // ✅ member_id로 회원 1명 조회
    @Override
    public Member findById(String member_id) {
        return memberRepository.findById(member_id);
    }

    // ✅ 모든 회원 목록 조회
    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // ✅ 회원 정보 수정
    @Override
    public void update(Member member) {
        memberRepository.update(member);
    }

    // ✅ 회원 삭제 (회원 탈퇴)
    @Override
    public void delete(String member_id) {
    	Member member = new Member();
    	member.setMember_id(member_id);
        member.setStatus(MemberStatus.INACTIVE); 
        memberRepository.update(member);
    }

    // ✅ 로그인 처리: member_id로 회원 조회 후 비밀번호는 Security 또는 Controller에서 확인
    @Override
    public Member login(String member_id) {
        return memberRepository.login(member_id);
    }


    // ✅ 관리자 계정 조회 (추후 is_admin 컬럼 사용시 구현 예정)
    @Override
    public List<Member> findAdmins() {
        // ❗ 아직 미구현 상태
        return null;
    }

    // ✅ ID 중복 여부 확인 (true면 중복 있음)
    @Override
    public boolean isDuplicateId(String member_id) {
        System.out.println("💡 Service 도착: 중복체크 대상 ID = " + member_id);
        return memberRepository.isDuplicateId(member_id);
    }

    // ✅ username 중복 여부 확인
    @Override
    public boolean isDuplicateUsername(String username) {
        return memberRepository.findByUsername(username) != null;
    }

    // ✅ 이름 + 전화번호로 아이디 찾기
    @Override
    public String findId(String name, String phone) {
        return memberRepository.findId(name, phone);
    }

    // ✅ 이름 + 전화번호로 member_id 및 created_at 같이 찾기 (아이디 찾기 확장)
    @Override
    public Member findIdWithCreatedAt(String name, String phone) {
        return memberRepository.findIdWithCreatedAt(name, phone);
    }

    // ✅ 이름 + 아이디 + 이메일로 회원 찾기 (비밀번호 재설정용)
    @Override
    public Member findByNameIdEmail(String name, String member_id, String email) {
        return memberRepository.findByNameIdEmail(name, member_id, email);
    }

    // ✅ 회원 비밀번호 변경
    @Override
    public void updatePassword(String member_id, String encodedPw) {
        memberRepository.updatePassword(member_id, encodedPw);
    }

	@Override
	public Member findByEmailToken(String token) {
		return memberRepository.findByEmailToken(token);
	}

	@Override
	public void verifyEmail(String member_id) {
		memberRepository.verifyEmail(member_id);
	}

	@Override
	public Member findByPhone(String phone) {
		return memberRepository.findByPhone(phone);
	}

	@Override
	public void updatePhoneCertInfo(String phone, String code, LocalDateTime createdAt) {
		memberRepository.updatePhoneCertInfo(phone, code, createdAt);
	}

	@Override
	public void updatePhoneVerified(String phone) {
		memberRepository.updatePhoneVerified(phone);
	}

	@Override
	public void updateLoginFailInfo(Member member) {
		memberRepository.updateLoginFailInfo(member);
	}

	@Override
	public void updateProfileImage(String member_id, String profileImage) {
		memberRepository.updateProfileImage(member_id, profileImage);
	}

	@Override
	public void sendTemporaryPassword(String name, String memberId, String email) {
		Member member = memberRepository.findByNameIdEmail(name, memberId, email);
		if(member == null) {
			throw new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다.");
		}
		
		String tempPassword = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPw = encoder.encode(tempPassword);
		memberRepository.updatePassword(memberId, encodedPw);
		mailService.sendTemporaryPasswordMail(email, tempPassword);
	}

	@Override
	public void updatePasswordWithTemp(String member_id, String encodedPw, String tempPw) {
		memberRepository.updatePasswordWithTemp(member_id, encodedPw, tempPw);
	}

	@Override
	public boolean existsByEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	@Override
	public Member updateAccountAndReturn(String memberId, int amount) {
		memberRepository.updateAccountBalance(memberId, amount);
		return memberRepository.findById(memberId); 
	}



	
}
