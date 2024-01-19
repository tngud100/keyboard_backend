package com.example.keyboard.repository;

import com.example.keyboard.entity.user.userEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface userDao  {
     public void join(userEntity user) throws Exception;
     public userEntity selectByIdAndPw(String userId) throws Exception;

}