package com.example.keyboard.service;

import com.example.keyboard.config.Redis.RedisUtils;
import com.example.keyboard.entity.member.MemberEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.keyboard.repository.UserDao;
@Service
@RequiredArgsConstructor
public class UserService {
    public final UserDao userDao;
    public final RedisUtils redisUtils;

    public String join(MemberEntity vo) throws Exception {
        MemberEntity userVO = new MemberEntity();
        String userId = vo.getLoginId();

        String existId = userDao.existsById(userId);

        if(existId != null){
            return "아이디가 중복입니다.";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        vo.setRole("ADMIN");

        userDao.join(vo);
        return  "회원가입 성공!";
    }



//    // 유효한 토큰인지 확인합니다. -> validation 진행
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//        // Redis에 해당 accessToken logout 여부를 확인
//        String isLogout = (String) redisTemplate.opsForValue().get(token);
//
//        // 로그아웃이 없는(되어 있지 않은) 경우 해당 토큰은 정상적으로 작동하기
//        if (ObjectUtils.isEmpty(isLogout)) {
//
//            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            // SecurityContext 에 Authentication 객체를 저장합니다.
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        }
//    }
    public void logout(String AccessToken, String RefreshToken) {
        boolean isRefreshToken  = redisUtils.validateToken(RefreshToken);
        // refreshToken이 redis에 있는지 유무를 확인한다.
        if(!isRefreshToken){
            redisUtils.deleteData(RefreshToken); // refreshToken redis에서 삭제하여 AccessToken 재발급 정지 시키기
        }

        // redis 블랙리스트에 현재의 AccessToken을 넣기
//        redisUtils.setData(AccessToken);

        }
    }

//    public Map<String, String> storeUserInfo() throws Exception {
//        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//
//
//        Map<String, String> result = new HashMap<>();
//        result.put("loginId", loginId);
//        result.put("role", role);
//
//        return  result;
//    }
//
//    public void storeRefreshTokenInfo(String refreshToken, String userId, int expirationMinutes) {
//        String key = "refreshToken:" + refreshToken;
//        HashMap<String, Object> tokenInfo = new HashMap<>();
//        tokenInfo.put("userId", userId);
//        // 만료 시간 등 추가 정보 저장 가능
//        redisUtil.set(key, tokenInfo, expirationMinutes);
//    }


}



