package com.example.keyboard.service;

import com.example.keyboard.config.JWT.JWTUtil;
import com.example.keyboard.config.Redis.RedisUtils;
import com.example.keyboard.entity.jwt.CustomUserDetails;
import com.example.keyboard.entity.member.MemberEntity;
import com.example.keyboard.repository.AuthDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    private final JWTUtil jwtUtil;
    private final RedisUtils redisUtils;

    public AuthService( JWTUtil jwtUtil, RedisUtils redisUtils) {
        this.jwtUtil = jwtUtil;
        this.redisUtils = redisUtils;
    }

    public boolean checkBlackList(String accessToken) throws Exception {
        return redisUtils.isTokenBlacklisted(accessToken);
    }

    public boolean isExpiredAccessToken(String accessToken) throws Exception{
        return jwtUtil.isExpired(accessToken); // true가 만료된 경우 혹은 오류
    }

    public String newAccessTokenByRefreshToken(String refreshToken) throws Exception {
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            System.out.println("access토큰이 만료 되었습니다, access토큰을 재발급합니다.");

            // Redis에서 리프레시 토큰 데이터를 가져옵니다.
            String refreshTokenDTO = redisUtils.getData(refreshToken);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> dataMap = objectMapper.readValue(refreshTokenDTO, new TypeReference<Map<String, String>>() {
            });

            String userId = (String) dataMap.get("userId");
            String role = (String) dataMap.get("role");

            return jwtUtil.createAccessToken(userId, role, 60 * 60 * 1000L); // 새 액세스 토큰 생성
        } else {
            // 리프레시 토큰이 유효하지 않은 경우, 적절한 에러 처리를 합니다.
            throw new Exception("리프레쉬 토큰이 유효하지 않습니다");
        }
    }

    public void setSecurityAuthenticationToken(String accessToken) throws Exception {
        String userId = jwtUtil.getUserId(accessToken);
        String role = jwtUtil.getRole(accessToken);

        //userEntity를 생성하여 값 set
        MemberEntity userEntity = new MemberEntity();
        userEntity.setLOGIN_ID(userId);
        userEntity.setPASSWORD("temppassword");
        userEntity.setROLE(role);

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //security에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
