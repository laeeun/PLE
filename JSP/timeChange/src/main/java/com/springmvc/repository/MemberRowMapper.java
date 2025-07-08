package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.Member;

public class MemberRowMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();

        member.setMemberId(rs.getString("memberId"));       // 회원 고유 ID
        member.setUserId(rs.getString("username"));          // 사용자 ID (로그인용)
        member.setPw(rs.getString("password"));              // 비밀번호
        member.setName(rs.getString("name"));                // 이름
        member.setEmail(rs.getString("email"));              // 이메일
        member.setPhone(rs.getString("phone"));              // 전화번호
        member.setAddr(rs.getString("address"));             // 주소
        member.setExpert(rs.getBoolean("is_expert"));      // 전문가 여부

        // created_at → LocalDateTime으로 변환
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            member.setCreatedAt(createdAt.toLocalDateTime());
        }

        return member;
    }
}
