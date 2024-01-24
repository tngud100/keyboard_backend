package com.example.keyboard.entity.member;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberEntity {
    private Long id;            // 회원번호
    private String loginId;     // 로그인 ID
    private String password;    // 비밀번호
    private String userName;    // 유저 이름
    private String phoneNum;    // 전화번호
    private String address;     // 주소
    private String email;       // 이메일
    private LocalDate birthday; // 생년월일
    private int deleteState;    // 탈퇴 여부    ENUM: 0 = 회원, 1 = 탈퇴
    private String role;        // 유저 권한

    public void clearPassword() {
    }
}