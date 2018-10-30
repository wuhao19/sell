package com.wuhao.sell.repository;

import com.wuhao.sell.domain.ProductInfo;
import com.wuhao.sell.enums.ProductStatusEnums;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("0000001");
        productInfo.setProductName("皮蛋瘦肉粥");
        productInfo.setProductPrice(new BigDecimal(5.4));
        productInfo.setProductStock(88);
        productInfo.setProductDescription("营养又美味，吃了之后提神醒脑！");
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("http://XXXXXXX.jpg");
        productInfo.setCategoryType(1);
        ProductInfo result = productRepository.save(productInfo);
        Assert.assertNotNull(result);
    }



    @Test
    public void findByProductStatus() {
        List<ProductInfo> list = productRepository.findByProductStatus(ProductStatusEnums.UP.getCode());
        Assert.assertNotEquals(0,list.size());
    }
}