package kr.msp.notUsed;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JwtUtil jwtUtil;
	
	public SecurityConfig(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
        .csrf().disable() // JWT를 사용할 때는 보통 CSRF 비활성화
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 생성하지 않음
        .and()
	        .authorizeHttpRequests()
	        .antMatchers("/api/auth/login").permitAll()
	        .antMatchers("/api/auth/**").authenticated()
        .anyRequest().permitAll() // 나머지 요청은 인증된 사용자만 접근 가능
	    .and()
	    .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가	    
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
