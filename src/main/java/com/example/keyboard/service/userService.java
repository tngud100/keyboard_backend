package com.example.keyboard.service;

import com.example.keyboard.entity.jwt.JwtToken;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.keyboard.entity.user.userEntity;
import com.example.keyboard.repository.userDao;

@Service
@RequiredArgsConstructor
public class userService {
    public final userDao userDao;

    public String join(userEntity vo) throws Exception {
        userEntity userVO = new userEntity();
        String userId = vo.getLoginId();

        if(userId == null){
            return null;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        userDao.join(vo);

        return vo.getUserName();
    }

//    public JwtToken login(String loginId, String password) throws Exception {
//        userEntity loginVO = new userEntity();
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        loginVO = userDao.selectByIdAndPw(loginId);
//
//        String encodedPassword = (loginVO == null) ? "" : loginVO.getPassword();
//
//        if (loginVO == null || passwordEncoder.matches(password, encodedPassword) == false) {
//            return null;
//        }
//
//        loginVO.clearPassword();
//
//        return loginVO;
//    }
}



