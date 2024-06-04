package com.example.keyboard.entity.board.inquire;

import lombok.Data;

@Data
public class InquireCommentEntity {
    private Long inquire_comment_id;     // 질문 답변 일련번호
    private Long member_id;              // 회원 정보 일련번호
    private Long inquires_id;            // 질문 게시판 번호
    private String comment;             // 답변 내용
    private String regdate;             // 답변 일시
}
