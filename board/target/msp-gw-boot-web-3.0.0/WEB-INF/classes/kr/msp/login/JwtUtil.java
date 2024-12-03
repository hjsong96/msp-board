package kr.msp.login;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
	
	private static final String SECRET_KEY = "hadine board msp project";
	private static final long EXPIRATION_TIME = 1000 * 60 * 60;
	
	private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	public String generateToken(String userID) {
		return Jwts.builder()
				.setSubject(userID)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public Claims validateToken(String token) throws JwtException {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	

}
