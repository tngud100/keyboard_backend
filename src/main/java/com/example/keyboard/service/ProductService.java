package com.example.keyboard.service;

import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import com.example.keyboard.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    public final ProductDao productDao;

    // 상품 등록
    public void listEnroll(ProductEntity vo) throws Exception {
        productDao.insertListProduct(vo);
    }
    // 상품 리스트 가져오기
    public List<ProductEntity> selectProductList() throws Exception{
        return productDao.selectListProduct();
    }
    // 상세 상품, 카테고리 등록
    public void insertProductDetail(ProductDetailEntity vo) throws  Exception{
        productDao.insertProductDetail(vo);
        System.out.println(vo);
        productDao.insertDetailCategory(vo);
    }
    // 상세 상품 가져오기
    public List<ProductDetailEntity> selectProductDetailList(Long productId) throws Exception{
        List<ProductDetailEntity> productDetail = productDao.selectProductDetailList(productId);
        List<ProductDetailEntity> productCategory = productDao.selectProductCategory(productId);
        List<ProductDetailEntity> productDetailEntity = new ArrayList<>();
        for( ProductDetailEntity productVO : productDetail){
            for( ProductDetailEntity categoryVO : productCategory ) {
                int categoryState = categoryVO.getCATEGORY_STATE();
                String categoryName = categoryVO.getCATEGORY_NAME();
                String name = productVO.getNAME();
                int amount = productVO.getAMOUNT();
                int stock = productVO.getSTOCK();

                ProductDetailEntity EntityVO = new ProductDetailEntity();
                EntityVO.setNAME(name);
                EntityVO.setAMOUNT(amount);
                EntityVO.setSTOCK(stock);
                EntityVO.setCATEGORY_NAME(categoryName);
                EntityVO.setCATEGORY_STATE(categoryState);

                productDetailEntity.add(EntityVO);
            }
        }

        return productDetailEntity;
    }


}
