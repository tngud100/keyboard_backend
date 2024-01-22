package com.example.keyboard.service;

import com.example.keyboard.entity.jwt.CustomUserDetails;
import com.example.keyboard.entity.member.memberEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.keyboard.repository.userDao;

@Service
@RequiredArgsConstructor
public class userService {
    public final userDao userDao;

    public String join(memberEntity vo) throws Exception {
        memberEntity userVO = new memberEntity();
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

//    public CustomUserDetails login(String loginId) throws Exception {
//        memberEntity loginVO = new memberEntity();
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        loginVO = userDao.findByLoginId(loginId);
//
//        if(loginVO != null){
//            return new CustomUserDetails(loginVO);
//        }
//
//        return null;

//        String encodedPassword = (loginVO == null) ? "" : loginVO.getPassword();
//
//        if (loginVO == null || passwordEncoder.matches(password, encodedPassword) == false) {
//            return null;
//        }
//
//        loginVO.clearPassword();
//    }
}



