package com.example.keyboard.entity.jwt;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "refresh_token")
public class BlackListToken {

    @Id
    private String authId;

    @Indexed
    private String token;

    public BlackListToken update(String authId) {
        this.token = authId;
        return this;
    }

}