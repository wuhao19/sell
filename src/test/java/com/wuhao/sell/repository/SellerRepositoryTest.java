package com.wuhao.sell.repository;

import com.wuhao.sell.domain.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerRepositoryTest {
    @Autowired
    private SellerRepository sellerRepository;
    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId("000001");
        sellerInfo.setSellerName("wuhao");
        sellerInfo.setSellerPassword("wuhao191919");
        SellerInfo result = sellerRepository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findSellerInfoBySellerId() {
        SellerInfo sellerInfo = sellerRepository.findSellerInfoBySellerId("000001");
        Assert.assertEquals("wuhao",sellerInfo.getSellerName());
    }

    @Test
    public void findSellerInfoBySellerNameAndSellerPassword(){
        SellerInfo result = sellerRepository.findSellerInfoBySellerNameAndSellerPassword("wuhao", "wuhao191919");
        Assert.assertNotNull(result);
    }
}