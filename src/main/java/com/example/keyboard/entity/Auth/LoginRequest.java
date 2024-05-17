package com.example.keyboard.entity.Auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String login_id;
    private String password;
}
