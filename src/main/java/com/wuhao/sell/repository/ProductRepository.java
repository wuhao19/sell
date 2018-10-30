package com.wuhao.sell.repository;

import com.wuhao.sell.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductInfo,String> {
    List<ProductInfo> findByProductStatus(Integer productStatus);
    ProductInfo findProductInfoByProductId(String productId);
}
