package com.example.keyboard.service;

import com.example.keyboard.controller.ImageController;
import com.example.keyboard.entity.Image.inquire.InquireDaoEntity;
import com.example.keyboard.entity.Image.inquire.InquireImageEntity;
import com.example.keyboard.entity.board.inquire.InquireCommentEntity;
import com.example.keyboard.entity.board.inquire.InquireEntity;
import com.example.keyboard.repository.ImageDao;
import com.example.keyboard.repository.InquireDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InquireService {
    public final InquireDao inquireDao;
    public final ImageDao imageDao;
    public final ImageController imageController;
    public Long enrollInquireBoard(InquireEntity inquireEntity) throws Exception{
        inquireDao.enrollInquireBoard(inquireEntity);
        return inquireEntity.getInquires_id();
    }
    public Boolean enrollInquireComments(Long member_id, Long inquire_id, String comments) throws Exception{
        inquireDao.enrollInquireComments(member_id, inquire_id, comments);
        inquireDao.updateInquireCommentState(inquire_id);
        return true;
    }


    public List<Map<String, Object>> selectAllInquireBoards() throws Exception{
        List<InquireEntity> inquireBoardList = inquireDao.selectAllInquireBoards();
        List<Map<String, Object>> result = new ArrayList<>();

        for(InquireEntity boardList : inquireBoardList){
            Map<String, Object> map = addInquireImage(boardList);
            result.add(map);
        };
        return result;
    }

    public List<Map<String, Object>> selectInquireBoardsByUserId(Long member_id) throws Exception{
        List<InquireEntity> InquireBoardList = inquireDao.selectInquireBoardsByMemberId(member_id);
        List<Map<String, Object>> result = new ArrayList<>();

        for( InquireEntity selectedInquireBoard : InquireBoardList){
            Map<String, Object> map = addInquireImage(selectedInquireBoard);
            result.add(map);
        }
        return result;
    }

    public Map<String, Object> selectInquireBoardByInquireId(Long inquires_id) throws Exception{
        InquireEntity inquireBoard = inquireDao.selectInquireBoardByInquireId(inquires_id);
        Map<String, Object> result = addInquireImage(inquireBoard);
        return result;
    }

    public boolean updateInquireBoard(InquireEntity inquireEntity) throws Exception{
        inquireDao.updateInquireBoard(inquireEntity);
        return true;
    }
    public Boolean updateInquireComments(Long inquire_id, String comments) throws Exception{
        inquireDao.updateInquireComments(inquire_id, comments);
        return true;
    }
    public Boolean updateInquirePicture(InquireImageEntity inquireImageEntity) throws Exception{
        List<Integer> fileIdx = new ArrayList<>();
        List<Integer> existedNameIdx = new ArrayList<>();
        Long inquires_id = inquireImageEntity.getInquires_id();

        List<InquireDaoEntity> beforeImages = imageDao.selectInquireImage(inquires_id);

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
                if (name.equals("null")) {
                    System.out.println("이름 없음");
                } else {
                    existedNameIdx.add(i);
                    System.out.println("이름 " + i + ": " + name);
                }
            }
        }

        // 기존 이미지에서 존재하는 파일 이름을 제외한 이미지를 삭제
        for (InquireDaoEntity beforeImage : beforeImages) {
            String beforeImageName = beforeImage.getPicture_name();
            if (!existedFileName.contains(beforeImageName)) {
                // 이미지 삭제 로직 호출 (imageController에 deleteImage 메서드가 있다고 가정)
                imageController.modifyInquireImg(null, beforeImageName, inquires_id);
                System.out.println("삭제된 이미지: " + beforeImageName);
            }
        }

        if(fileIdx.isEmpty()){
            return false;
        }

        for (int i = 0; i < fileIdx.size(); i++){
            int idx = fileIdx.get(i); // fileIdx 리스트에서 현재 인덱스를 가져옴
            String imgName = existedFileName.get(idx); // existedNameIdx 리스트에서 해당 인덱스에 해당하는 값을 가져와 출력
            MultipartFile imgfile = pictures.get(idx);

            imageController.modifyInquireImg(imgfile, imgName, inquires_id);
        }

        return true;
    }



    public boolean deleteInquireBoard(Long inquire_id) throws Exception{
        inquireDao.deleteInquire(inquire_id);
        inquireDao.deleteInquireComment(inquire_id);
//        imageDao.deleteInquirePicturesById(inquire_id);
        return true;
    }

    public Map<String, Object> addInquireImage(InquireEntity InquireBoardContent) throws Exception{
        Long inquires_id = InquireBoardContent.getInquires_id();
        int comment_state = InquireBoardContent.getComment_state();
        List<InquireDaoEntity> inquireImageList = imageDao.selectInquireImage(inquires_id);

        Map<String, Object> map = new HashMap<>();
        map.put("inquire", InquireBoardContent);
        map.put("images", inquireImageList);

        if(comment_state != 0){
            InquireCommentEntity InquireComment = inquireDao.selectInquireComment(inquires_id);
            map.put("comment", InquireComment);
        }


        return map;
    }
}
