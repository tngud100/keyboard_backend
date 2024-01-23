package com.example.keyboard.config.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Component
public class JWTUtil {

    private Key key;

    public JWTUtil(@Value("${jwt.secret}") String secret) {
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
    }

    public String getUserId(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("userId", String.class);
    }

    public String getRole(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public String createAccessToken(String userId, String role, long expirationMs) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("userId", userId);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(long expirationMs) {
        Claims claims = Jwts.claims();
        // Optionally, you could add a claim to indicate this is a refresh token
        claims.put("type", "refresh");

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }



    public boolean validateRefreshToken(String refreshToken) {
        try {
            // 리프레시 토큰을 파싱합니다. JWT 생성 시 사용된 동일한 키를 사용하세요.
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key) // 토큰 생성에 사용된 동일한 키를 설정합니다.
                    .build()
                    .parseClaimsJws(refreshToken);

            // 예를 들어, 만료를 명시적으로 확인합니다(단, JJWT는 이를 자동으로 처리합니다):
            boolean isTokenExpired = claims.getBody().getExpiration().before(new Date());
            if (isTokenExpired) {
                return false; // 토큰이 만료되었습니다.
            }

            // 필요한 경우, 토큰이 취소되지 않았는지 확인합니다. 예를 들어, 데이터베이스에서 조회하는 방법 등이 있습니다.

            return true; // 토큰이 유효합니다.
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰이 유효하지 않습니다. 예외를 필요에 따라 로깅합니다.
            return false;
        }
    }

    public String renewAccessToken(String userId, String role, Long expiredMs) {
        // 새로운 accessToken 생성 로직
        return createAccessToken(userId, role, expiredMs); // 유효 시간은 예시로, 필요에 따라 조정
    }
}