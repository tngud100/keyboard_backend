package com.example.keyboard.service;

import com.example.keyboard.entity.member.MemberEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.keyboard.repository.UserDao;
@Service
@RequiredArgsConstructor
public class UserService {
    public final UserDao userDao;

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



