package com.wuhao.sell.util;

import com.wuhao.sell.domain.OrderMaster;
import com.wuhao.sell.dto.OrderDto;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * orderMaster转换为orderDto
 */
public class OrderMasterTOOrderDto {
    public static OrderDto convet(OrderMaster orderMaster){
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return orderDto;
    }

    public static List<OrderDto> convet(List<OrderMaster> orderMasterList){
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (OrderMaster orderMaster : orderMasterList){
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderMaster,orderDto);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
}
