package com.example.keyboard.repository;

import com.example.keyboard.entity.Image.ImageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ImageDao {
    public void saveProductImage(ImageEntity imageVO) throws Exception;
    public ImageEntity selectPictureByPictureId(@Param("img_id") Long img_id) throws Exception;
    public void deleteProductPicture(@Param("img_id") Long img_id) throws Exception;

}
