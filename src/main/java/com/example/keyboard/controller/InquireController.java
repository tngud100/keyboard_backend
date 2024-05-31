package com.example.keyboard.controller;

import com.example.keyboard.entity.Image.inquire.InquireDaoEntity;
import com.example.keyboard.entity.Image.inquire.InquireImageEntity;
import com.example.keyboard.entity.board.inquire.InquireEntity;
import com.example.keyboard.service.InquireService;
import com.example.keyboard.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "1:1 문의 게시판 관련 API", description = "문의등록, 답변관련 CRUD")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/inquire")
public class InquireController {
    public final InquireService inquireService;
    public final ImageController imageController;

    @Operation(summary = "문의 등록", description = "문의 등록하기")
    @PostMapping("/enroll")
    public ResponseEntity<Object> enrollInquireBoard(InquireEntity inquireEntity){
        try{
            Long inquires_id = inquireService.enrollInquireBoard(inquireEntity);
            return new ResponseEntity<>(inquires_id, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "문의 사진 등록", description = "문의 등록하기")
    @PostMapping("/pictures/enroll")
    public ResponseEntity<Object> enrollInquirePicture(InquireImageEntity inquireDaoEntity){
        try{
            List<MultipartFile> Images = inquireDaoEntity.getPictures();
            Long inquires_id = inquireDaoEntity.getInquires_id();
            imageController.uploadInquireImg(Images, inquires_id);

            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "해당 유저의 문의 검색", description = "해당 유저의 문의 등록 게시글 가져오기")
    @GetMapping("/get/memberNum/{member_id}")
    public ResponseEntity<Object> selectInquireBoard(@PathVariable("member_id") Long member_id){
        try{
            List<Map<String, Object>> inquireEntity = inquireService.selectInquireBoards(member_id);
            return new ResponseEntity<>(inquireEntity, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "문의 번호를 통한 문의 내용 검색", description = "문의 번호를 통한 문의 내용 검색")
    @GetMapping("/get/inquireNum/{inquire_id}")
    public ResponseEntity<Object> selectInquireBoardByInquireId(@PathVariable("inquire_id") Long inquires_id){
        try{
            InquireEntity inquireEntity = inquireService.selectInquireBoardByInquireId(inquires_id);
            List<InquireDaoEntity> inquireImages = inquireService.selectInquireImages(inquires_id);
            Map<String, Object> result = new HashMap<>();
            result.put("inquire", inquireEntity);
            result.put("images", inquireImages);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "해당 유저의 문의 수정", description = "해당 유저의 등록된 문의 게시글 수정")
    @PutMapping("/update/{inquires_id}")
    public ResponseEntity<Object> updateInquireBoard(InquireEntity inquireEntity){
        try{
            boolean isUpdated = inquireService.updateInquireBoard(inquireEntity);
            return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "문의 사진 수정", description = "문의 사진 수정하기")
    @PutMapping("/update/pictures/{inquire_id}")
    public ResponseEntity<Object> updateInquirePicture(@ModelAttribute InquireImageEntity inquireImageEntity) {
        try {
            List<Integer> fileIdx = new ArrayList<>();
            List<Integer> existedNameIdx = new ArrayList<>();
            Long inquires_id = inquireImageEntity.getInquires_id();

            List<MultipartFile> pictures = inquireImageEntity.getPictures();
            if (pictures == null) {
                System.out.println("파일 없음");
            } else {
                for (int i = 0; i < pictures.size(); i++) {
                    MultipartFile file = pictures.get(i);
                    if (file == null) {
                        System.out.println("파일 없음");
                    } else {
                        fileIdx.add(i);
                        System.out.println("파일 " + i + ": " + file);
                    }
                }
            }

            List<String> existedFileName = inquireImageEntity.getExistedFileName();
            if (existedFileName == null) {
                System.out.println("이름 없음");
            } else {
                for (int i = 0; i < existedFileName.size(); i++) {
                    String name = existedFileName.get(i);
                    if (name == null) {
                        System.out.println("이름 없음");
                    } else {
                        existedNameIdx.add(i);
                        System.out.println("이름 " + i + ": " + name);
                    }
                }
            }

            if(fileIdx.isEmpty()){
                return new ResponseEntity<>("변경된 사진 없음", HttpStatus.OK);
            }

            for (int i = 0; i < fileIdx.size(); i++){
                int idx = fileIdx.get(i); // fileIdx 리스트에서 현재 인덱스를 가져옴
                String imgName = existedFileName.get(idx); // existedNameIdx 리스트에서 해당 인덱스에 해당하는 값을 가져와 출력
                MultipartFile imgfile = pictures.get(idx);

                imageController.modifyInquireImg(imgfile, imgName, inquires_id);
            }


            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




    @Operation(summary = "해당 유저의 문의 삭제", description = "해당 유저의 등록된 문의 게시글 삭제")
    @DeleteMapping("/delete/{inquires_id}")
    public ResponseEntity<Object> deleteInquireBoard(@PathVariable("inquires_id") Long inquire_id){
        try{
            boolean isDeleted = inquireService.deleteInquireBoard(inquire_id);
            return new ResponseEntity<>(isDeleted, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
