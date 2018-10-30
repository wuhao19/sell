package com.wuhao.sell.service;

import com.wuhao.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    /** 创建订单 **/
    OrderDto createOrder(OrderDto orderDto);

    /** 查询单个订单 **/
    OrderDto findOneOrder(String orderId);

    /** 查询订单列表 **/
    Page<OrderDto> findList(String OrederOpenid, Pageable pageable);

    /** 取消订单 **/
    OrderDto cancel(OrderDto orderDto);

    /** 完结订单 **/
    OrderDto finish(OrderDto orderDto);

    /** 支付订单 **/
    OrderDto pay(OrderDto orderDto);

    /** 查询所有订单 **/
    Page<OrderDto> findList( Pageable pageable);

}
