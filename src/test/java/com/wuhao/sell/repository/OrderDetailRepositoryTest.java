package com.wuhao.sell.repository;

import com.wuhao.sell.domain.OrderDetail;
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
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("464645456");
        orderDetail.setDetailId("464654654");
        orderDetail.setProductId("4654654654");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(9.5));
        orderDetail.setProductIcon("http://23231.jpg");
        orderDetail.setProductQuantity(3);
        OrderDetail result = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> result = orderDetailRepository.findByOrderId("464645456");

        Assert.assertNotEquals(0,result.size());
    }
}