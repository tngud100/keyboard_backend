package com.example.keyboard.service;

import com.example.keyboard.entity.jwt.CustomUserDetails;
import com.example.keyboard.repository.AuthDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.keyboard.entity.member.MemberEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthDao authDao;

    public CustomUserDetailsService(AuthDao authDao) {

        this.authDao = authDao;
    }


    // 로그인시에 load하여 userDetails호출
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        //DB에서 조회
        MemberEntity userData = new MemberEntity();
        try {
            System.out.println(userId+"아이디를 찾습니다");
            userData = authDao.findByLoginId(userId);
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