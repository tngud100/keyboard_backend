package com.example.keyboard.entity.board.inquire;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class InquireEntity {
    private Long inquires_id;    // 질문 게시판 번호
    private Long member_id;      // 회원 정보 id
    private String title;          // 질문 제목
    private String content;        // 질문 내용
    private String inquire_type;    // 질문 종류
    private String regdate;         // 질문 작성 날짜
    private int comment_state;  // 답변 상태    0(답변 대기 중), 1(답변 완료)
    private String isdelete;        // 삭제 여부
}
