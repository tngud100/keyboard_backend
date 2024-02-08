package com.example.keyboard.repository;

import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ProductDao {

    public void insertListProduct(ProductEntity productVO) throws Exception;

    public List<ProductEntity> selectListProduct() throws Exception;

    public void insertProductDetail(ProductDetailEntity productDetailVO) throws Exception;
    public void insertDetailCategory(ProductDetailEntity productDetailVO) throws Exception;

    public List<ProductDetailEntity> selectProductDetailList(Long productId) throws Exception;
    public List<ProductDetailEntity> selectProductCategory(Long productId) throws Exception;

}

