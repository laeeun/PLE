package com.springmvc.config;

import javax.sql.DataSource;

import com.springmvc.controller.ForgotController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // ✅ 이 클래스가 스프링 설정 클래스라는 의미
@EnableWebSecurity // ✅ Spring Security 기능을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ForgotController forgotController;
	
	@Autowired
	private LoginFailureHandler loginFailureHandler;

	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private DataSource dataSource;

    SecurityConfig(ForgotController forgotController) {
        this.forgotController = forgotController;
    } 

    // 🔐 인증 및 인가 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // ✅ CSRF 보호 비활성화 (개발단계에선 꺼두는 게 편함)
            .csrf().disable()

            // ✅ 접근 권한 설정 시작
            .authorizeRequests()
                // 🔓 로그인 없이 접근 가능한 경로 설정 (회원가입, 아이디/비밀번호 찾기 포함)
                .antMatchers(
                    "/", "/login", "/logout",
                    "/signUp", 
                    "/findId", "/findPw",
                    "/resources/**" // 📁 CSS, JS, 이미지 등 정적 리소스 허용
                ).permitAll()

                // 🔒 관리자 페이지는 ROLE_ADMIN만 접근 가능
                .antMatchers("/admin/**").hasRole("ADMIN")

                // 🔒 그 외 모든 경로는 로그인한 사용자만 접근 가능
                .anyRequest().authenticated()

            .and() // 다음 설정으로 이동

            // ✅ 로그인 설정
            .formLogin()
                .loginPage("/login") // 사용자 정의 로그인 페이지 경로
                .loginProcessingUrl("/login") // 로그인 처리 POST 요청 경로
                .usernameParameter("member_id") // 로그인 폼에서 ID input name
                .passwordParameter("pw")        // 로그인 폼에서 비밀번호 input name
                .successHandler(loginSuccessHandler)                
                .failureUrl("/login?error=true")
                .failureHandler(loginFailureHandler)
                .defaultSuccessUrl("/", true)
                .permitAll() // 로그인 페이지 접근 모두 허용

            .and()

            // ✅ 로그아웃 설정
            .logout()
                .logoutSuccessUrl("/login?logout") // 로그아웃 후 이동 경로
                .permitAll(); // 로그아웃 경로는 모두 허용
        
    }

    // ✅ 사용자 인증 처리 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	System.out.println("사용자 인증 처리 입장!!!!!!!!!!!!!!!!!!");
    	System.out.println(auth.toString());
        auth
            .jdbcAuthentication() // JDBC 기반 인증 사용
            .dataSource(dataSource) // 사용할 DB 연결 설정

            // 🔎 사용자 인증 쿼리
            .usersByUsernameQuery(
                "SELECT member_id, pw, TRUE FROM member WHERE member_id = ?"
            )
            // - member_id: 사용자 ID
            // - pw: 암호화된 비밀번호
            // - TRUE: 계정이 활성화 상태인지 여부 (true로 고정)

            // 🔎 권한 조회 쿼리
            .authoritiesByUsernameQuery(
                "SELECT member_id, role FROM member WHERE member_id = ?"
            )
            // - role 컬럼 값: 예) ROLE_USER, ROLE_ADMIN

            // 🔒 암호화 방식 설정 (BCrypt 사용)
            .passwordEncoder(new BCryptPasswordEncoder());
        	System.out.println("사용자 인증 처리 퇴장!!!!!!!!!!!!!!!!!!");
    }
}
