package com.example.keyboard.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.keyboard.entity.user.userEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 세션에서 회원 정보 조회
        HttpSession session = request.getSession();
        userEntity member = (userEntity) session.getAttribute("loginMember");

        // 2. 회원 정보 체크
        if (member == null || member.getDeleteState() == 1) {
            response.sendRedirect("/login.do");
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
