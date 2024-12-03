package kr.msp.login;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
	
	private final Key key;
	private final long expirationTime;
	
	public JwtUtil(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.expiration-time}") long expirationTime) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
		this.expirationTime = expirationTime;
	}
	
	public String generateToken(String userID) {
		return Jwts.builder()
				.setSubject(userID)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public Claims validateToken(String token) throws JwtException {
	    try {
	        return Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    } catch (ExpiredJwtException e) {
	        throw new JwtException("JWT token이 만료되었습니다. 재로그인 바랍니다.");
	    }
	}
}
