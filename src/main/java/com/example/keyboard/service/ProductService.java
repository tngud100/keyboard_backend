package com.example.keyboard.service;

import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import com.example.keyboard.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    public final ProductDao productDao;

    public void listEnroll(ProductEntity vo) throws Exception {
        productDao.insertListProduct(vo);
    }

    public List<ProductEntity> selectProductList() throws Exception{
        return productDao.selectListProduct();
    }

//    public void insertProductDetail(ProductDetailEntity vo) throws  Exception{
//        productDao.insertProductDetail(vo);
//    }
}
