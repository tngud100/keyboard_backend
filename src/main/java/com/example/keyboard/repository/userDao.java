package com.example.keyboard.repository;

import com.example.keyboard.entity.member.memberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Mapper
@Repository
public interface userDao  {
     public void join(memberEntity user) throws Exception;

     public String existsById(String userId) throws Exception;

     public memberEntity findByLoginId(String userId) throws Exception;

}