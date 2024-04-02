package com.example.keyboard.repository;

import com.example.keyboard.entity.product.ImageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ImageDao {
    public void saveProductImage(ImageEntity imageVO) throws Exception;
}
