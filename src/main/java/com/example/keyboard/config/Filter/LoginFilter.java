package com.example.keyboard.config.Filter;

import com.example.keyboard.config.JWT.JWTUtil;
import com.example.keyboard.entity.jwt.CustomUserDetails;
import com.example.keyboard.config.Redis.RedisUtils;
import com.example.keyboard.entity.jwt.RefreshToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RedisUtils redisUtils;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RedisUtils redisUtils) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.redisUtils = redisUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

            //클라이언트 요청에서 username, password 추출
            String login_id = request.getParameter("login_id");
            String password = request.getParameter("password");

            //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login_id, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        //UserDetailsS
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String accessToken = jwtUtil.createAccessToken(userId, role, 60*60*100*10L); // 1시간
        String refreshToken = jwtUtil.createRefreshToken(60*60*24*100*10L); // 1일

        RefreshToken refreshTokenDto = RefreshToken.builder()
                .authId(userId)
                .token(refreshToken)
                .role(role)
                .ttl(60*60*24*100*10L)
                .build();

        redisUtils.saveRefreshTokenInfo(refreshTokenDto);


        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("Refresh-Token", refreshToken);

        System.out.println("로그인 성공 토큰 발급 완료");
//        System.out.println(tokenDto);
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("로그인 실패");
        //로그인 실패시 401 응답 코드 반환
        response.setStatus(401);
    }
}
