package com.example.keyboard.service;

import com.example.keyboard.config.JWT.JWTUtil;
import com.example.keyboard.config.Redis.RedisUtils;
import com.example.keyboard.config.SMS.SmsUtil;
import com.example.keyboard.entity.member.MemberEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.keyboard.repository.AuthDao;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    public final AuthDao authDao;
    public final RedisUtils redisUtils;
    public final JWTUtil jwtUtil;
    public final SmsUtil smsUtil;

    @Autowired
    public AuthService(JWTUtil jwtUtil, RedisUtils redisUtils, AuthDao authDao, SmsUtil smsUtil) {
        this.jwtUtil = jwtUtil;
        this.redisUtils = redisUtils;
        this.authDao = authDao;
        this.smsUtil = smsUtil;
    }

    public String join(MemberEntity vo) throws Exception {
        MemberEntity userVO = new MemberEntity();
        String userId = vo.getLOGIN_ID();

        String existId = authDao.existsById(userId);
    
        if(existId != null){
            return "아이디가 중복입니다.";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        vo.setPASSWORD(passwordEncoder.encode(vo.getPASSWORD()));

        authDao.join(vo);
        return  "회원가입 성공!";
    }

    public void logout(String AccessToken, String RefreshToken) {
        // refreshToken이 redis에 있는지 유무를 확인한다.
        if(RefreshToken != null && jwtUtil.validateRefreshToken(RefreshToken)){
            redisUtils.deleteData(RefreshToken); // refreshToken redis에서 삭제하여 AccessToken 재발급 정지 시키기
            System.out.println("RefreshToken 제거");
        }
        // redis 블랙리스트에 현재의 AccessToken을 넣기
        if(!jwtUtil.isExpired(AccessToken)){
            redisUtils.addAccessTokenToBlackList(AccessToken);
            System.out.println("블랙리스트 추가 완료");
        }
    }

    public void sendVerifyNum(String phoneNum){
        Random random = new Random();
        // 6자리 랜덤 값 입력
        int verificationCode = random.nextInt(900000) + 100000;
        String verificationCodeStr = String.valueOf(verificationCode);

        // 인증번호 발신
        smsUtil.sendOne(phoneNum, verificationCodeStr);
        //인증코드 유효기간 3분 설정
        redisUtils.setData(phoneNum, verificationCodeStr,30 * 60 * 10 * 10L);
    }

    public boolean checkVerifyNum(String phoneNum, String verifyNum){
        String certificationNum = redisUtils.getData(phoneNum);
        return certificationNum.equals(verifyNum);
    }


    public Map<String, String> check(String token){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        String tokenExceptBeerer = token.split(" ")[1];

        Map<String, String> security = new HashMap<String, String>();

        security.put("security name", name);
        security.put("security role", role);

        String tokenId = jwtUtil.getUserId(tokenExceptBeerer);
        String tokenRole = jwtUtil.getRole(tokenExceptBeerer);

        security.put("token userId", tokenId);
        security.put("token role", tokenRole);


        return security;
    }
}



