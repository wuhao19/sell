package com.wuhao.sell.service;

import com.wuhao.sell.dto.OrderDto;

/**
 * 买家安全的订单服务
 */
public interface BuyerService {
    OrderDto findOrderOne(String openid,String orderId);
    void cancelOrder(String openid,String orderId);
}
