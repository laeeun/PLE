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

        member.setMember_id(rs.getString("member_id"));       // 회원 고유 ID
        member.setUserName(rs.getString("username"));         // 사용자 계정 ID
        member.setPw(rs.getString("pw"));                    // 비밀번호
        member.setName(rs.getString("name"));                // 이름
        member.setEmail(rs.getString("email"));              // 이메일
        member.setPhone(rs.getString("phone"));              // 전화번호
        member.setAddr(rs.getString("addr"));                // 주소
        member.setExpert(rs.getBoolean("expert"));           // 전문가 여부

        // created_at → LocalDateTime으로 변환
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            member.setCreatedAt(createdAt.toLocalDateTime());
        }

        return member;
    }
}
