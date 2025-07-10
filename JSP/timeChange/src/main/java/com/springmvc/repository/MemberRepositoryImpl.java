package com.springmvc.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.Member;

@Repository
public class MemberRepositoryImpl implements MemberRepository{
	
	private JdbcTemplate template; // DB 작업을 쉽게 처리해주는 객체
	
	@Autowired
	private MemberRepository memberRepository;
	
	private List<Member> memberList = new ArrayList<Member>(); //member리스트 생성
	
	@Autowired //spring이 자동으로 JdbcTemplate 객체를 주입해줌
	public void setJdbcTemplate(JdbcTemplate template) {
		
		this.template = template;
	}

	@Override
	public void save(Member member) {
		String sql = "INSERT INTO member(member_id, username, pw, name, email, phone, addr, expert, created_at) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		template.update(sql,
				member.getMember_id(),
				member.getUserName(),
				member.getPw(),
				member.getName(),
				member.getEmail(),
				member.getPhone(),
				member.getAddr(),
				member.isExpert(),
				Timestamp.valueOf(member.getCreatedAt())
		);
		
	}

	@Override
	public Member findById(String member_id) {
		//ID로 특정 멤버 조회
		String sql = "SELECT * FROM member WHERE member_id = ?";
		List<Member> members = template.query(sql, new Object[] {member_id}, new MemberRowMapper());
		
		if (members.isEmpty()) {
	        return null;  // 존재하지 않으면 null 반환
	    }
		
		 return members.get(0);
	}

	@Override
	public List<Member> findAll() {
		//전체 멤버 조회 
		String sql = "SELECT * FROM member";
		List<Member> members = template.query(sql, new MemberRowMapper());
		return members;
	}

	@Override
	public void update(Member member) {
		//회원 정보 수정
		String sql = "UPDATE member SET pw = ?, name = ?, email = ?, phone = ?, addr = ?, expert = ? WHERE member_id = ? ";
		template.update(
		sql,
		member.getPw(),
		member.getName(),
		member.getEmail(),
		member.getPhone(),
		member.getAddr(),
		member.isExpert(),
		member.getMember_id()
		);
	}

	@Override
	public void delete(String member_id) {
		//회원 삭제
		String sql = "DELETE FROM member WHERE member_id = ? ";
		template.update(sql, member_id);
	}

	@Override
	public Member login(String memberId) {
		//로그인 처리
		String sql = "SELECT * FROM member WHERE member_id = ?";
		List<Member> members =template.query(sql, new Object[] {memberId}, new MemberRowMapper());
		
		if(members.isEmpty()) {
			return null;
		}
		return members.get(0);
	}

	@Override
	public Member findByEmail(String email) {
		//이메일로 회원 찾기
		String sql = "SELECT * FROM member WHERE email = ?";
		return null;
	}


	@Override
	public List<Member> findAdmins() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String toString() {
		return "MemberRepositoryImpl [template=" + template + ", memberList=" + memberList + "]";
	}

	@Override
	public Member findByUsername(String username) {
		String sql = "SELECT * FROM member WHERE username = ?";
        List<Member> members = template.query(sql, new Object[]{username}, new MemberRowMapper());
        return members.isEmpty() ? null : members.get(0);
	}

	@Override
	public Member findIdWithCreatedAt(String name, String phone) {
		 return memberRepository.findIdWithCreatedAt(name, phone);
	}

	@Override
	public Member findByNameIdEmail(String name, String member_id, String email) {
		String sql = "SELECT * FROM member WHERE name = ? AND member_id = ? AND email = ?";
		List<Member> members = template.query(sql, new MemberRowMapper(), name, member_id, email);
        return members.isEmpty() ? null : members.get(0);
	}

	
	
	
	
}
