package com.example.keyboard.entity.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class userEntity {
    private Long id;            // 회원번호
    private String loginId;     // 로그인 ID
    private String password;    // 비밀번호
    private String userName;    // 유저 이름
    private String phoneNum;    // 전화번호
    private String address;     // 주소
    private String email;       // 이메일
    private LocalDate birthday; // 생년월일
    private int deleteState;    // 탈퇴 여부    ENUM: 0 = 회원, 1 = 탈퇴

    public void clearPassword() {
    }
}
//id int AI PK
//login_id varchar(45)
//password varchar(64)
//user_name varchar(45)
//phone_number varchar(45)
//address varchar(45)
//email varchar(45)
//birthday date
//user_type varchar(45)
//delete_state tinyint(1)
//created_date datetime
//modified_date datetime