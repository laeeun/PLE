package com.springmvc.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.MemberDTO;

@Repository // 이 클래스가 Spring에서 사용할 Repository(DAO) 클래스라는 표시
public class MemberRepositoryImpl implements MemberRepository {

    private JdbcTemplate template; // ✅ DB 작업을 편하게 도와주는 스프링 JDBC 도구

    private List<MemberDTO> memberList = new ArrayList<MemberDTO>(); // ❗사용하지 않는 필드 (지워도 무방함)

    @Autowired // Spring이 JdbcTemplate을 자동으로 주입해줌
    public void setJdbcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    // ✅ 회원 저장
    @Override
    public void save(MemberDTO member) {
        String sql = "INSERT INTO member(member_id, username, pw, name, email, phone, addr, expert, created_at, account) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql,
            member.getMember_id(),
            member.getUserName(),
            member.getPw(),
            member.getName(),
            member.getEmail(),
            member.getPhone(),
            member.getAddr(),
            member.isExpert(),
            Timestamp.valueOf(member.getCreatedAt()),
            member.getAccount()
        );
    }

    // ✅ 회원 ID로 조회 (회원 상세보기 등)
    @Override
    public MemberDTO findById(String member_id) {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        List<MemberDTO> members = template.query(sql, new Object[]{member_id}, new MemberRowMapper());

        return members.isEmpty() ? null : members.get(0); // 결과가 없으면 null
    }

    // ✅ 전체 회원 조회 (관리자 페이지 등에서 사용 가능)
    @Override
    public List<MemberDTO> findAll() {
        String sql = "SELECT * FROM member";
        return template.query(sql, new MemberRowMapper());
    }

    // ✅ 회원 정보 수정
    @Override
    public void update(MemberDTO member) {
        String sql = "UPDATE member SET name = ?, email = ?, phone = ?, addr = ?, expert = ? WHERE member_id = ?";
        template.update(sql,
            member.getName(),
            member.getEmail(),
            member.getPhone(),
            member.getAddr(),
            member.isExpert(),
            member.getMember_id()
        );
    }

    // ✅ 회원 삭제
    @Override
    public void delete(String member_id) {
        String sql = "DELETE FROM member WHERE member_id = ?";
        template.update(sql, member_id);
    }

    // ✅ 로그인용: member_id로 회원 1명 조회
    @Override
    public MemberDTO login(String memberId) {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        List<MemberDTO> members = template.query(sql, new Object[]{memberId}, new MemberRowMapper());

        return members.isEmpty() ? null : members.get(0);
    }

    // ✅ 관리자 전체 조회 (아직 구현 안됨 - TODO)
    @Override
    public List<MemberDTO> findAdmins() {
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
    public MemberDTO findByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        List<MemberDTO> members = template.query(sql, new Object[]{username}, new MemberRowMapper());

        return members.isEmpty() ? null : members.get(0);
    }

    // ✅ 이름 + 전화번호로 회원의 member_id를 조회 (created_at까지 포함하려면 RowMapper 수정 필요)
    @Override
    public MemberDTO findIdWithCreatedAt(String name, String phone) {
        String sql = "SELECT member_id FROM member WHERE name = ? AND phone = ?";
        try {
            return template.queryForObject(sql, new Object[]{name, phone}, new MemberRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // ✅ 비밀번호 변경 (암호화된 비밀번호 적용 시 사용)
    @Override
    public void updatePassword(String member_id, String encodedPw) {
        String sql = "UPDATE member SET pw = ? WHERE member_id = ?";
        template.update(sql, encodedPw, member_id);
    }

    // ✅ 이름 + 아이디 + 이메일로 회원 찾기 (비밀번호 재설정 시 본인 확인용)
    @Override
    public MemberDTO findByNameIdEmail(String name, String member_id, String email) {
        String sql = "SELECT * FROM member WHERE name = ? AND member_id = ? AND email = ?";
        List<MemberDTO> members = template.query(sql, new MemberRowMapper(), name, member_id, email);

        return members.isEmpty() ? null : members.get(0);
    }

    // ✅ toString() 오버라이드 (디버깅용)
    @Override
    public String toString() {
        return "MemberRepositoryImpl [template=" + template + ", memberList=" + memberList + "]";
    }
}
