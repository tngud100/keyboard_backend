package com.example.keyboard.service;

import com.example.keyboard.repository.product.ProductDetailEntity;
import com.example.keyboard.repository.product.ProductEntity;
import com.example.keyboard.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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
        productDao.insertDetailCategory(vo);
    }
    // 상세 상품 가져오기
    public List<ProductDetailEntity> selectProductDetailList(Long productId) throws Exception{
        List<ProductDetailEntity> productDetail = productDao.selectProductDetailList(productId);
        List<ProductDetailEntity> productDetailEntity = new ArrayList<>();

        for( ProductDetailEntity productVO : productDetail){
            Long productDetailId = productVO.getProduct_detail_id();
            List<ProductDetailEntity> productCategory = productDao.selectProductCategory(productDetailId);

            ProductDetailEntity EntityVO = new ProductDetailEntity();

            EntityVO.setProduct_id(productVO.getProduct_id());
            EntityVO.setProduct_detail_id(productDetailId);
            EntityVO.setName(productVO.getName());
            EntityVO.setAmount(productVO.getAmount());
            EntityVO.setStock(productVO.getStock());
            EntityVO.setDefault_state(productVO.getDefault_state());

            for( ProductDetailEntity categoryVO : productCategory ) {
                int categoryState = categoryVO.getCategory_state();
                String categoryName = categoryVO.getCategory_name();

                EntityVO.setCategory_name(categoryName);
                EntityVO.setCategory_state(categoryState);
            }
            productDetailEntity.add(EntityVO);
        }

        return productDetailEntity;
    }

    // 상세 상품 기본값 설정
    public void setProductDetailDefault(Long product_id, Integer product_detail_id) throws Exception{
        productDao.updateProductDefault(product_id, product_detail_id);
    }

    // 상품 리스트에서 메인으로 설정
    public void setMainProduct(ProductEntity vo) throws Exception{
        String main_pic_path = vo.getMain_picture();
        Integer main_pic_state = vo.getMain_pic_state();
        Long product_id = vo.getProduct_id();

        productDao.insertMainPic(product_id, main_pic_path);
        productDao.updateMainPicState(product_id, main_pic_state);
    }
}
