package com.example.keyboard.entity.member;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberEntity {
    private Long MEMBER_ID;             // 회원번호
    private String LOGIN_ID;            // 로그인 ID
    private String PASSWORD;            // 비밀번호
    private String USER_NAME;           // 유저 이름
    private String PHONE_NUM;           // 전화번호
    private String ADDRESS;             // 주소
    private String ZIPCODE;             // 우편번호
    private String ADDRESS_DETAIL;      // 상세 주소
    private String EMAIL;               // 이메일
    private LocalDate BIRTHDAY;         // 생년월일
    private String ROLE;                // 유저 권한
    private int DELETE_STATE;           // 탈퇴 여부    ENUM: 0 = 회원, 1 = 탈퇴
    private LocalDate CREATED_DATE;     // 가입 날짜
    private LocalDate MODIFIED_DATE;    // 수정 날짜

    public void clearPassword() {
    }
}