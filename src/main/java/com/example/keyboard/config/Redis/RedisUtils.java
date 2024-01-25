package com.example.keyboard.config.Redis;

import com.example.keyboard.entity.jwt.RefreshToken;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
public class RedisUtils {

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

    public void saveRefreshTokenInfo(RefreshToken refreshTokenDto) {
        String refreshToken = refreshTokenDto.getToken();
        String userId = refreshTokenDto.getAuthId();
        String role = refreshTokenDto.getRole();
        long expirationMs = refreshTokenDto.getTtl();

        // 사용자 ID와 역할을 JSON 형식의 문자열로 저장할 수도 있습니다.
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