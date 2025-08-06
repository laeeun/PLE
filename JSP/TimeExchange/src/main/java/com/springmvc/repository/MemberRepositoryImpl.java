package com.springmvc.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.Member;
import com.springmvc.domain.PasswordHistoryDTO;

@Repository // 이 클래스가 Spring에서 사용할 Repository(DAO) 클래스라는 표시
public class MemberRepositoryImpl implements MemberRepository {

    private JdbcTemplate template; // ✅ DB 작업을 편하게 도와주는 스프링 JDBC 도구


    @Autowired // Spring이 JdbcTemplate을 자동으로 주입해줌
    public void setJdbcTemplate(JdbcTemplate template) {
        this.template = template;
    }
    
    @Autowired
    private PasswordHistoryRepository passwordHistoryRepository;

    // ✅ 회원 저장
    @Override
    public void save(Member member) {
        String sql = "INSERT INTO member(member_id, username, pw, name, email, phone, birthDate, gender, addr, addr_detail, expert, created_at, account, profile_image) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        template.update(sql,
            member.getMember_id(),                    // member_id
            member.getUserName(),                     // username
            member.getPw(),                           // pw
            member.getName(),                         // name
            member.getEmail(),                        // email
            member.getPhone(),                        // phone
            member.getBirthDate(),                    // birthDate
            member.getGender(),                       // gender
            member.getAddr(),                         // addr
            member.getAddrDetail(),                   // addr_detail
            member.getExpert(),                        // expert
            Timestamp.valueOf(member.getCreatedAt()), // created_at
            member.getAccount(),                      // ✅ account 먼저
            member.getProfileImage()                  // ✅ profile_image 다음
        );
    }


    // ✅ 회원 ID로 조회 (회원 상세보기 등)
    @Override
    public Member findById(String member_id) {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        List<Member> members = template.query(sql, new Object[]{member_id}, new MemberRowMapper());

        return members.isEmpty() ? null : members.get(0); // 결과가 없으면 null
    }

    // ✅ 전체 회원 조회 (관리자 페이지 등에서 사용 가능)
    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM member";
        return template.query(sql, new MemberRowMapper());
    }

    // ✅ 회원 정보 수정
    @Override
    public void update(Member member) {
        String sql = "UPDATE member SET username = ?, name = ?, email = ?, phone = ?, birthDate = ?, gender = ?, addr = ?, addr_detail = ?, expert = ?, email_verified = ?, email_token = ?, token_created_at = ?, profile_image = ? WHERE member_id = ?";
        
        template.update(sql,
            member.getUserName(),              
            member.getName(),
            member.getEmail(),
            member.getPhone(),
            member.getBirthDate(),
            member.getGender(),
            member.getAddr(),
            member.getAddrDetail(),
            member.getExpert(),
            member.isEmailVerified(),
            member.getEmailToken(),
            member.getTokenCreatedAt(),
            member.getProfileImage(),
            member.getMember_id()
        );
    }


    // ✅ 회원 삭제
    @Override
    public void delete(String member_id) {
        String sql = "UPDATE member SET status = 'INACTIVE' WHERE member_id = ?";
        template.update(sql, member_id);
    }

    // ✅ 로그인용: member_id로 회원 1명 조회
    @Override
    public Member login(String memberId) {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        List<Member> members = template.query(sql, new Object[]{memberId}, new MemberRowMapper());

        return members.isEmpty() ? null : members.get(0);
    }

    // ✅ 관리자 전체 조회 (아직 구현 안됨 - TODO)
    @Override
    public List<Member> findAdmins() {
        return null;
    }

    // ✅ 이름 + 전화번호로 아이디 찾기 (아이디 찾기 기능)
    @Override
    public String findId(String name, String phone) {
        String sql = "SELECT member_id FROM member WHERE name = ? AND phone = ?";
        try {
            return template.queryForObject(sql, new Object[]{name, phone}, String.class);
        } catch (Exception e) {
            return null;
        }
    }

    // ✅ username으로 회원 조회 (중복체크나 로그인 등에 사용 가능)
    @Override
    public Member findByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        List<Member> members = template.query(sql, new Object[]{username}, new MemberRowMapper());

        return members.isEmpty() ? null : members.get(0);
    }

    // ✅ 이름 + 전화번호로 회원의 member_id를 조회 (created_at까지 포함하려면 RowMapper 수정 필요)
    @Override
    public Member findIdWithCreatedAt(String name, String phone) {
    	 String sql = "SELECT * FROM member WHERE name = ? AND phone = ?";
        try {
        	  return template.queryForObject(sql, new MemberRowMapper(), name, phone);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // ✅ 비밀번호 변경 (암호화된 비밀번호 적용 시 사용)
    @Override
    public void updatePassword(String member_id, String encodedPw) {
        // 1. member 테이블의 비밀번호 업데이트
    	String sql = "UPDATE member SET pw = ?, temp_password = NULL, temp_pw_created_at = NULL WHERE member_id = ?";
        template.update(sql, encodedPw, member_id);

        // 2. password_history 테이블에 이력 저장
        PasswordHistoryDTO history = new PasswordHistoryDTO();
        history.setMember_id(member_id);
        history.setPassword_hash(encodedPw);
        history.setChanged_at(LocalDateTime.now());
        passwordHistoryRepository.save(history);
    }

    // ✅ 이름 + 아이디 + 이메일로 회원 찾기 (비밀번호 재설정 시 본인 확인용)
    @Override
    public Member findByNameIdEmail(String name, String member_id, String email) {
        String sql = "SELECT * FROM member WHERE name = ? AND member_id = ? AND email = ?";
        List<Member> members = template.query(sql, new MemberRowMapper(), name, member_id, email);

        return members.isEmpty() ? null : members.get(0);
    }


    @Override
    public Member findByEmailToken(String token) {
        String sql = "SELECT * FROM member WHERE email_token = ?";
        
        try {
            // ✅ token을 제대로 바인딩해야 함
            return template.queryForObject(sql, new MemberRowMapper(), token);
        } catch (EmptyResultDataAccessException e) {
            // 결과가 없을 때는 null 반환 (혹은 예외 throw 가능)
            return null;
        }
    }

	@Override
	public void verifyEmail(String member_id) {
		String sql = "UPDATE member SET email_verified = true WHERE member_id = ?";
		template.update(sql, member_id);
		
	}

	@Override
	public Member findByPhone(String phone) {
	    String sql = "SELECT * FROM member WHERE phone = ?";
	    return template.queryForObject(sql, new MemberRowMapper(), phone);
	}


	@Override
	public void updatePhoneVerified(String phone) {
	    String sql = "UPDATE member SET phone_verified = true WHERE phone = ?";
	    template.update(sql, phone);
	}

	@Override
	public void updatePhoneCertInfo(String phone, String code, LocalDateTime createdAt) {
	    String sql = "UPDATE member SET phone_cert_code = ?, phone_cert_created_at = ?, phone_verified = false WHERE phone = ?";
	    template.update(sql, code, createdAt, phone);
	}

	@Override
	public void updateLoginFailInfo(Member member) {
		String sql = "UPDATE member SET login_fail_count = ?, last_fail_time = ? WHERE member_id = ?";
		template.update(sql, member.getLoginFailCount(), member.getLastFailTime(), member.getMember_id());
	}

	@Override
	public void updateProfileImage(String member_id, String profileImage) {
		String sql = "UPDATE member SET profile_image_name = ? WHERE member_id = ?";
		template.update(sql, profileImage, member_id);
	}

	@Override
	public void updatePasswordWithTemp(String member_id, String encodedPw, String tempPw) {
		String sql = "UPDATE member SET pw = ?, temp_password = ?, temp_pw_created_at = NOW() WHERE member_id = ?";
		template.update(sql, encodedPw, tempPw, member_id);
	}

	@Override
	public boolean existsByEmail(String email) {
	    String sql = "SELECT COUNT(*) FROM member WHERE email = ?";
	    Integer count = template.queryForObject(sql, Integer.class, email);
	    return count != null && count > 0;
	}

	@Override
	public void updateAccountBalance(String memberId, int amount) {
		 String sql = "UPDATE member SET account = account + ? WHERE member_id = ?";
		 template.update(sql, amount, memberId);
	}
	
	@Override
	public boolean isDuplicateId(String member_id) {
	    System.out.println("🛠️ 중복 체크할 ID: " + member_id); // 확인용
	    String sql = "SELECT COUNT(*) FROM member WHERE member_id = ?";
	    Integer count = template.queryForObject(sql, Integer.class, member_id);
	    return count != null && count > 0;
	}
   
	@Override
	public boolean existsByUsernameExceptMe(String username, String myId) {
	    String sql = "SELECT COUNT(*) FROM member WHERE username = ? AND member_id != ?";
	    Integer count = template.queryForObject(sql, Integer.class, username, myId);
	    return count != null && count > 0;
	}
}
