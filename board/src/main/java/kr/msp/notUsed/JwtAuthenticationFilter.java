package kr.msp.notUsed;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = getTokenFromRequest(request);

        if (token != null && !token.isEmpty()) {
            try {
                String userID = jwtUtil.validateToken(token).getSubject();
                if (userID != null) {
                    UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(userID, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                // 토큰이 만료된 경우 처리
                logger.error("JWT token has expired: {}", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 만료되었습니다. 다시 로그인해 주세요.");
                return;
            } catch (JwtException e) {
                // JWT 검증 실패한 경우 처리
                logger.error("JWT token validation failed: {}", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "잘못된 토큰입니다.");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
