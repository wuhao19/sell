package com.wuhao.sell.service.serviceImpl;

import com.wuhao.sell.domain.ProductInfo;
import com.wuhao.sell.enums.ProductStatusEnums;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;
    @Test
    public void findOne() {
        ProductInfo result = productService.findOne("0000001");
        Assert.assertEquals("0000001",result.getProductId());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        System.out.println(productInfoPage.getContent());
        Assert.assertNotNull(productInfoPage.getContent());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = productService.findUpAll();
        Assert.assertNotEquals(0,upAll.size());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("0000002");
        productInfo.setProductName("红烧鸡翅");
        productInfo.setProductPrice(new BigDecimal(7.6));
        productInfo.setProductStock(999);
        productInfo.setProductDescription("香味悠长，源自老重庆的味道");
        productInfo.setProductIcon("http:79787897.jpg");
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());
        productInfo.setCategoryType(2);
        ProductInfo requst = productService.save(productInfo);
        Assert.assertNotNull(requst);
    }
    @Test
    public void lowerFrame(){
        ProductInfo productInfo = productService.lowerFrame("0000001");
        Assert.assertNotNull(productInfo);
    }
    @Test
    public void upFrame(){
        ProductInfo productInfo = productService.upFrame("0000001");
        Assert.assertNotNull(productInfo);
    }

}