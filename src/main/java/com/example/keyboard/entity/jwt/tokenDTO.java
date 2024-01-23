package com.example.keyboard.entity.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class tokenDTO {
    private String grantType; // JWT에 대한 인증 타입. 여기서는 Bearer를 사용한다.
    private String accessToken;
    private String refreshToken;

    public tokenDTO() {

    }
}