package com.springmvc.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.springmvc.domain.Member;

public class MemberRepositoryImpl implements MemberRepository{
	
	private JdbcTemplate template; // DB 작업을 쉽게 처리해주는 객체
	
	private List<Member> memberList = new ArrayList<Member>(); //member리스트 생성
	
	@Autowired //spring이 자동으로 JdbcTemplate 객체를 주입해줌
	public void setJdbcTemplate(JdbcTemplate template) {
		
		this.template = template;
	}

	@Override
	public void save(Member member) {
		String sql = "INSERT INTO member(memberId, userId, pw, name, eamil, phone, addr, isExpert, createAt) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		template.update(sql,
				member.getMemberId(),
				member.getUserId(),
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
	public Member findById(String memberId) {
		//ID로 특정 멤버 조회
		String sql = "SELECT * FROM member WHERE username = ?";
		Member member = template.queryForObject(sql, new Object[] {memberId}, new MemberRowMapper());
		 return member;
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
		String sql = "UPDATE member SET pw = ?, name = ?, email = ?, phone = ?, addr = ?, is_expert = ? WHERE memberId = ? ";
		template.update(
				
		member.getPw(),
		member.getName(),
		member.getEmail(),
		member.getPhone(),
		member.getAddr(),
		member.isExpert()
		
		);
	}

	@Override
	public void delete(String memberId) {
		String sql = "DELETE FROM member WHERE memberId = ? ";
		template.update(sql, memberId);
	}

	@Override
	public Member login(String memberId, String pw) {
		String sql = "SELECT * FROM member";
		return null;
	}

	@Override
	public Member findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findId(String name, String phone) {
		// TODO Auto-generated method stub
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
	
	
	
	
}
