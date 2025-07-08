package com.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.springmvc.domain.Member;

public class MemberServiceImpl implements MemberService{
	
	private JdbcTemplate template; // DB 작업을 쉽게 처리해주는 객체
	
	private List<Member> memberList = new ArrayList<Member>(); //member리스트 생성

	@Override
	public void save(Member member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Member findById(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Member member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String memberId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Member login(String memberId, String password) {
		// TODO Auto-generated method stub
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
	
}
