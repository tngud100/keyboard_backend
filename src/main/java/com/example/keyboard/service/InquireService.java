package com.example.keyboard.service;

import com.example.keyboard.entity.Image.inquire.InquireDaoEntity;
import com.example.keyboard.entity.Image.inquire.InquireImageEntity;
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
    public Long enrollInquireBoard(InquireEntity inquireEntity) throws Exception{
        inquireDao.enrollInquireBoard(inquireEntity);
        return inquireEntity.getInquires_id();
    }
    public Boolean enrollInquireComments(Long member_id, Long inquire_id, String comments) throws Exception{
        inquireDao.enrollInquireComments(member_id, inquire_id, comments);
        return true;
    }

    public List<InquireDaoEntity> selectInquireImages(Long inquires_id) throws Exception{
        return imageDao.selectInquireImage(inquires_id);
    }

    public List<Map<String, Object>> selectInquireBoards(Long member_id) throws Exception{
        List<InquireEntity> InquireBoardList = inquireDao.selectInquireBoardsByMemberId(member_id);
        List<Map<String, Object>> result = new ArrayList<>();

        for( InquireEntity selectedInquireBoard : InquireBoardList){
            Long inquires_id = selectedInquireBoard.getInquires_id();
            List<InquireDaoEntity> inquireImageList = imageDao.selectInquireImage(inquires_id);
            Map<String, Object> map = new HashMap<>();
            map.put("inquire", selectedInquireBoard);
            map.put("images", inquireImageList);

            result.add(map);
        }

        return result;
    }

    public InquireEntity selectInquireBoardByInquireId(Long inquires_id) throws Exception{
        return inquireDao.selectInquireBoardByInquireId(inquires_id);
    }

    public boolean updateInquireBoard(InquireEntity inquireEntity) throws Exception{
        inquireDao.updateInquireBoard(inquireEntity);
        return true;
    }
    public boolean deleteInquireBoard(Long inquire_id) throws Exception{
        inquireDao.deleteInquire(inquire_id);
        inquireDao.deleteInquireComment(inquire_id);
//        imageDao.deleteInquirePicturesById(inquire_id);
        return true;
    }
}
