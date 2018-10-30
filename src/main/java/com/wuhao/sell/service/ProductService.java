package com.wuhao.sell.service;

import com.wuhao.sell.domain.ProductInfo;
import com.wuhao.sell.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    Page<ProductInfo> findAll(Pageable pageable);

    List<ProductInfo> findUpAll();

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDto> cartDtoList);
    //减库存
    void decreaserStock(List<CartDto> cartDtoList);
    //商品上架下架
    ProductInfo lowerFrame(String productId);
    ProductInfo upFrame(String productId);
}
