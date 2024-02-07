package com.example.keyboard.repository;

import com.example.keyboard.entity.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductDao {

    public void insertListProduct(ProductEntity productVO) throws Exception;

    public List<ProductEntity> selectListProduct() throws Exception;
}

