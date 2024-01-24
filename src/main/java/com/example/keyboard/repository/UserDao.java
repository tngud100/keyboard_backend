package com.example.keyboard.repository;

import com.example.keyboard.entity.member.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
     public void join(MemberEntity user) throws Exception;

     public String existsById(String userId) throws Exception;

     public MemberEntity findByLoginId(String userId) throws Exception;

}