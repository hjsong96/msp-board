//package kr.msp.notUsed;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//	
//	private final Key key;
//	private final long expirationTime;
//	
//	public JwtUtil(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.expiration-time}") long expirationTime) {
//		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
//		this.expirationTime = expirationTime;
//	}
//	
//	public String generateToken(String userID, int userRank) {
//		return Jwts.builder()
//				.setSubject(userID)
//				.setIssuedAt(new Date())
//				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//				.claim("role", userRank)
//				.signWith(key, SignatureAlgorithm.HS256)
//				.compact();
//	}
//	
//	public Claims validateToken(String token) throws JwtException {
//	    try {
//	        return Jwts.parserBuilder()
//	                .setSigningKey(key)
//	                .build()
//	                .parseClaimsJws(token)
//	                .getBody();
//	    } catch (ExpiredJwtException e) {
//	        throw new JwtException("JWT token이 만료되었습니다. 재로그인 바랍니다.");
//	    }
//	}
//	
//	// JWT에서 userRank (role) 값을 추출
//	public int getUserRole(String token) {
//		Claims claims = validateToken(token);  // 토큰을 검증한 후 Claims 객체 얻기
//		return (Integer) claims.get("role");  // role claim을 추출
//	}
//
//	// JWT에서 userID 추출
//	public String getUserID(String token) {
//		Claims claims = validateToken(token);  // 토큰을 검증한 후 Claims 객체 얻기
//		return claims.getSubject();  // subject는 userID
//	}
//	
//}
