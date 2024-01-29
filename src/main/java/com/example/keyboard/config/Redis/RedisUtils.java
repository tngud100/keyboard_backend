package com.example.keyboard.config.Redis;

import com.example.keyboard.entity.jwt.RefreshToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;
@Service
public class RedisUtils {
    private Key key;

    @Value("${jwt.secret}")
    private String secret;

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setData(String key, String value, Long expiredTime) {
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public void addAccessTokenToBlackList(String accessToken){
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
        String userId = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().get("userId", String.class);
        Long leftAccessExpirationMs = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().get("exp", Long.class);

        Long expirationMs =  leftAccessExpirationMs;
        System.out.println(new Date(System.currentTimeMillis()).getTime());

        setData("blackList: "+accessToken, userId, expirationMs);
    }
    public boolean isTokenBlacklisted(String token){
        String key = "blackList: " + token;
        String value = getData(key);
        return value != null;
    }

    public void saveRefreshTokenInfo(RefreshToken refreshTokenDto) {
        String refreshToken = refreshTokenDto.getToken();
        String userId = refreshTokenDto.getAuthId();
        String role = refreshTokenDto.getRole();
        long expirationMs = refreshTokenDto.getTtl();

        // 사용자 ID와 역할을 JSON 형식의 문자열로 저장.
        // 예: {"userId":"user123", "role":"USER"}
        String userInfo = String.format("{\"userId\":\"%s\", \"role\":\"%s\" }", userId, role);
        setData(refreshToken, userInfo, expirationMs);
    }

    // 리프레시 토큰으로부터 사용자 정보를 조회하는 메소드 추가
    public boolean refreshTokenExists(String refreshToken) {
        String userInfoJson = getData(refreshToken);
        return userInfoJson != null && !userInfoJson.isEmpty();
    }

}