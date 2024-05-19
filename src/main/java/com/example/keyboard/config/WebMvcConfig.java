package com.example.keyboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoggerInterceptor())
//                .excludePathPatterns("/css/**", "/images/**", "/js/**");
//
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .addPathPatterns("/**/*.do")
//                .excludePathPatterns("/log*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("file:/Users/kimsoohyeong/Desktop/keyboard_backend/src/main/resources/static/images/");
//                .addResourceLocations("file:/src/main/resources/static/images");
    }


}
