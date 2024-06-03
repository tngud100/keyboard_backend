package com.example.keyboard.repository;

import com.example.keyboard.entity.board.inquire.InquireEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InquireDao {
    public void enrollInquireBoard(InquireEntity inquireEntity) throws Exception;
    public void enrollInquireComments(@Param("member_id") Long member_id, @Param("inquire_id") Long inquire_id, @Param("comments") String comments)

    public List<InquireEntity> selectInquireBoardsByMemberId(@Param("member_id") Long member_id) throws Exception;
    public InquireEntity selectInquireBoardByInquireId(@Param("inquires_id") Long inquires_id) throws Exception;

    public void updateInquireBoard(InquireEntity inquireEntity) throws Exception;

    public void deleteInquire(Long inquire_id) throws Exception;
    public void deleteInquireComment(Long inquire_id) throws Exception;
}
