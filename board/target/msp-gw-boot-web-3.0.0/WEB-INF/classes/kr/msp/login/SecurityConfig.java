package kr.msp.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // CSRF(크로스 사이트 요청 위조 공격 방지) 보호 비활성화 (JWT를 사용할 때는 보통 비활성화)
			.authorizeRequests()
				.antMatchers("/api/user/join", "/api/auth/login").permitAll() //로그인과 회원가입은 누구나 접근 가능
				.anyRequest().authenticated() //나머지 요청은 인증된 사용자만 접근 가능
			.and()
			.formLogin().loginPage("/api/auth/login").permitAll()
			.and()
			.logout().permitAll(); //로그아웃 페이지 설정
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
