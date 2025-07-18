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

        // 기본 회원 정보
        member.setMember_id(rs.getString("member_id"));       // 회원 고유 ID
        member.setUserName(rs.getString("username"));         // 사용자 계정 ID
        member.setPw(rs.getString("pw"));                     // 비밀번호
        member.setName(rs.getString("name"));                 // 이름
        member.setEmail(rs.getString("email"));               // 이메일
        member.setPhone(rs.getString("phone"));               // 전화번호
        member.setGender(rs.getString("gender"));             // "M" or "F"
        member.setAddr(rs.getString("addr"));                 // 주소
        member.setExpert(rs.getBoolean("expert"));            // 전문가 여부
        member.setAccount(rs.getInt("account"));

        // 생년월일
        Timestamp birth = rs.getTimestamp("birthDate");
        if (birth != null) {
            member.setBirthDate(birth.toLocalDateTime().toLocalDate());
        }

        // 생성일
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            member.setCreatedAt(createdAt.toLocalDateTime());
        }

        // ✅ 추가된 이메일 인증 관련 필드
        member.setEmailVerified(rs.getBoolean("email_verified"));

        String token = rs.getString("email_token");
        if (token != null) {
            member.setEmailToken(token);
        }

        Timestamp tokenTime = rs.getTimestamp("token_created_at");
        if (tokenTime != null) {
            member.setTokenCreatedAt(tokenTime.toLocalDateTime());
        }

        return member;
    }
}
