package com.example.keyboard.service;

import com.example.keyboard.entity.jwt.CustomUserDetails;
import com.example.keyboard.repository.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.keyboard.entity.member.MemberEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {

        this.userDao = userDao;
    }


    // 로그인시에 load하여 userDetails호출
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        //DB에서 조회
        MemberEntity userData = new MemberEntity();
        try {
            userData = userDao.findByLoginId(userId);
            if (userData != null) {
                //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
                return new CustomUserDetails(userData);
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}