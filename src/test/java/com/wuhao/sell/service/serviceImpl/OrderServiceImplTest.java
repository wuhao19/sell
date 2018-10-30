package com.wuhao.sell.service.serviceImpl;

import com.wuhao.sell.domain.OrderDetail;
import com.wuhao.sell.dto.OrderDto;
import com.wuhao.sell.enums.OrderStatusEnum;
import com.wuhao.sell.enums.PayEnum;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    private final String OPEND_ID = "00002222";
    @Autowired
    private OrderServiceImpl orderService;
    @Test
    public void creatOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderOpenid(OPEND_ID);
        orderDto.setOrderAddress("重庆市璧山区");
        orderDto.setOrderPhone("17723938580");
        orderDto.setOrderName("吴浩");

        List<OrderDetail> orderDetailList = new ArrayList<>();


        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("0000002");
        orderDetail2.setProductQuantity(2);
        orderDetailList.add(orderDetail2);

        orderDto.setOrderDetailList(orderDetailList);
        OrderDto result = orderService.createOrder(orderDto);
        log.info("【创建订单】 result = {}", result);
        Assert.assertNotNull(result);
    }
    @Test
    public void findOneOrder(){
        OrderDto result = orderService.findOneOrder("1540478584403280394");
        System.out.println(result);
        Assert.assertNotNull(result);
    }
    @Test
    public void findList(){
        PageRequest pageRequest = new PageRequest(1,2);
        Page<OrderDto> result = orderService.findList(OPEND_ID, pageRequest);
        Assert.assertNotEquals(0,result.getTotalElements());
    }
    @Test
    public void cancel(){
        OrderDto orderDto = orderService.findOneOrder("1540479453112479395");
        OrderDto result = orderService.cancel(orderDto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }
    @Test
    public void finish(){
        OrderDto orderDto = orderService.findOneOrder("1540479453112479395");
        OrderDto result = orderService.finish(orderDto);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }
    @Test
    public void pay(){
        OrderDto orderDto = orderService.findOneOrder("1540479453112479395");
        OrderDto result = orderService.pay(orderDto);
        Assert.assertEquals(PayEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void findListAll(){
        PageRequest pageRequest = new PageRequest(1,2);
        Page<OrderDto> result = orderService.findList(pageRequest);
        Assert.assertNotEquals(0,result.getTotalElements());
    }
}