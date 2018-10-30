package com.wuhao.sell.service.serviceImpl;

import com.wuhao.sell.dto.OrderDto;
import com.wuhao.sell.enums.ResultEnum;
import com.wuhao.sell.excption.SellExcption;
import com.wuhao.sell.service.BuyerService;
import com.wuhao.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;
    @Override
    public OrderDto findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public void cancelOrder(String openid, String orderId) {
         orderService.cancel(checkOrderOwner(openid,orderId));
    }

    /**
     * 检查订单操作的安全性
     * @param openid
     * @param orderId
     * @return
     */
    private OrderDto checkOrderOwner(String openid,String orderId){
        OrderDto orderDto = orderService.findOneOrder(orderId);
        if (orderDto==null){
            log.info("【查询订单详情】没有找到,orderDto={}",orderDto);
            throw new SellExcption(ResultEnum.ORDER_NOT_EXIST);
        }
        if(!orderDto.getOrderOpenid().equals(openid)){
            log.info("【查询订单详情】没有订单的查询权限 orderOpenid={},openid={}",orderDto.getOrderOpenid(),openid);
            throw new SellExcption(ResultEnum.ORDER_FIND_ERROR);
        }
        return orderDto;
    }
}
