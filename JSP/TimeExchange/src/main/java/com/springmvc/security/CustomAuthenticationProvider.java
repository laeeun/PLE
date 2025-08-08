package com.springmvc.security;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.springmvc.domain.Member;
import com.springmvc.enums.MemberStatus;
import com.springmvc.service.MemberService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("[DEBUG] 입력한 username: " + username);
        System.out.println("[DEBUG] 입력한 password: " + password);
        
        Member member = memberService.login(username);
        System.out.println("[DEBUG] 조회된 member: " + member);
        
        if (member == null) {
        	 System.out.println("[DEBUG] 로그인 실패 - 존재하지 않는 회원");
            throw new BadCredentialsException("존재하지 않는 회원입니다.");
        }
        
     // 계정 상태 확인 (탈퇴 또는 정지된 경우 로그인 차단)
        if (member.getStatus() == MemberStatus.INACTIVE || member.getStatus() == MemberStatus.SUSPENDED) {
            throw new DisabledException("비활성화된 계정입니다.");
        }
        System.out.println("[DEBUG] DB에서 가져온 role: " + member.getRole());
        // 🔐 로그인 잠금 처리 (6회 이상 10분 차단)
        if (member.getLoginFailCount() >= 6) {
            if (member.getLastFailTime() != null &&
                member.getLastFailTime().isAfter(LocalDateTime.now().minusMinutes(10))) {
                throw new BadCredentialsException("로그인 실패가 6회 이상입니다. 10분 후 다시 시도해주세요.");
            } else {
                // 차단 해제
                member.setLoginFailCount(0);
                member.setLastFailTime(null);
                memberService.updateLoginFailInfo(member);
            }
        }
        System.out.println("[DEBUG] DB 비밀번호 해시: " + member.getPw());
        boolean matches = passwordEncoder.matches(password, member.getPw());
        System.out.println("[DEBUG] 비밀번호 매칭 결과: " + matches);
        System.out.println(new BCryptPasswordEncoder().encode("1111"));
        // 🔐 비밀번호 불일치
        if (!matches) {
        	System.out.println("[DEBUG] 로그인 실패 - 비밀번호 불일치");
            member.setLoginFailCount(member.getLoginFailCount() + 1);
            member.setLastFailTime(LocalDateTime.now());
            memberService.updateLoginFailInfo(member);
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        System.out.println("[DEBUG] 이메일 인증 상태: " + member.isEmailVerified());
        // 🔒 이메일 인증 체크 (선택)
        if (!member.isEmailVerified()) {
        	System.out.println("[DEBUG] 로그인 실패 - 이메일 인증 미완료");
            throw new BadCredentialsException("이메일 인증이 필요합니다.");
        }
        System.out.println("[DEBUG] 로그인 성공");
        // ✅ 로그인 성공 시 실패 기록 초기화
        member.setLoginFailCount(0);
        member.setLastFailTime(null);
        memberService.updateLoginFailInfo(member);

        String role = member.getRole();
        System.out.println("[DEBUG] DB에서 가져온 role: " + role);  // 👉 로그 추가

        if (role == null || role.isBlank()) {
            role = "ROLE_USER"; // 기본값으로 처리
            System.out.println("[DEBUG] role 값이 없어서 기본값으로 설정: ROLE_USER");
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        // ✅ Authentication에 Member 객체를 넣어서 추후 세션에서 사용 가능
        return new UsernamePasswordAuthenticationToken(member, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
