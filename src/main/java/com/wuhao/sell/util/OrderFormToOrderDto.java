package com.wuhao.sell.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wuhao.sell.domain.OrderDetail;
import com.wuhao.sell.dto.OrderDto;
import com.wuhao.sell.enums.ResultEnum;
import com.wuhao.sell.excption.SellExcption;
import com.wuhao.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
@Slf4j
public class OrderFormToOrderDto {
    public static OrderDto convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderName(orderForm.getName());
        orderDto.setOrderAddress(orderForm.getAddress());
        orderDto.setOrderPhone(orderForm.getPhone());
        orderDto.setOrderOpenid(orderForm.getOpenid());
        try {
            List<OrderDetail> orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
            orderDto.setOrderDetailList(orderDetailList);
            System.out.println(orderDto);
            return orderDto;
        }catch (Exception e){
            log.error("【对象转换】错误，string={}",orderForm.getItems());
            throw new SellExcption(ResultEnum.ORDER_FOEM_ERROR);
        }
    }
}
