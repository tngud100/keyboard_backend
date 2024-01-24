package com.example.keyboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class KeyboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeyboardApplication.class, args);
    }

}
