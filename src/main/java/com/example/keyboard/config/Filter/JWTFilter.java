package com.example.keyboard.config.Filter;

import com.example.keyboard.config.JWT.JWTUtil;
import com.example.keyboard.entity.jwt.CustomUserDetails;
import com.example.keyboard.entity.member.memberEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        System.out.println("authorization now");
        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            String refreshToken = request.getHeader("Refresh-Token"); // 리프레시 토큰을 요청 헤더에서 가져옵니다.
            if (refreshToken != null && jwtUtil.validateRefreshToken(refreshToken)) {
                System.out.println("access토큰이 만료, access토큰 재발급");
                // 리프레시 토큰이 유효한 경우, 새로운 액세스 토큰을 생성합니다.

//                String newAccessToken = jwtUtil.renewAccessToken(userId, role, 60 * 60 * 10L); // 새 액세스 토큰 생성

                // 새로운 액세스 토큰을 응답 헤더에 추가합니다.
//                response.addHeader("Authorization", "Bearer " + newAccessToken);

//                token = newAccessToken;
            } else {
                // 리프레시 토큰이 유효하지 않은 경우, 적절한 에러 처리를 합니다.
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid refresh token");
                filterChain.doFilter(request, response);
                return;
            }
        }


        //토큰에서 username과 role 획득
        String userId = jwtUtil.getUserId(token);
        String role = jwtUtil.getRole(token);

        System.out.println("user이름:"+userId);

        //userEntity를 생성하여 값 set
        memberEntity userEntity = new memberEntity();
        userEntity.setLoginId(userId);
        userEntity.setPassword("temppassword");
        userEntity.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}