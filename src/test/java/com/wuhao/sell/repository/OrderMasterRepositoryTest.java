package com.wuhao.sell.repository;

import com.wuhao.sell.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    private final String OPENID = "000055";

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setOrderName("大灰狼");
        orderMaster.setOrderPhone("17723938580");
        orderMaster.setOrderAddress("重庆市璧山区111路");
        orderMaster.setOrderOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(77));
        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderOpenid() {
        PageRequest request = new PageRequest(0,1);
        Page<OrderMaster> result = orderMasterRepository.findByOrderOpenid(OPENID, request);
        Assert.assertNotEquals(0,result.getTotalElements());
    }
}