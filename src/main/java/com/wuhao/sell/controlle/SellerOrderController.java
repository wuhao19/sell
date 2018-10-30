package com.wuhao.sell.controlle;

import com.wuhao.sell.dto.OrderDto;
import com.wuhao.sell.enums.ResultEnum;
import com.wuhao.sell.excption.SellExcption;
import com.wuhao.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 买家端订单
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {
    @Autowired
    private OrderService orderService;
    /**
     * 卖家端订单列表
     * @param page 第几页
     * @param size 每页几条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<OrderDto> orderDtoPage = orderService.findList(pageRequest);
        map.put("orderDtoPage",orderDtoPage);
        map.put("currenPage",page);
        map.put("size",size);
        return new ModelAndView("order/list",map);
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        try {
            OrderDto orderDto = orderService.findOneOrder(orderId);
            orderService.cancel(orderDto);
        }catch (SellExcption e){
            log.error("【买家端订单取消】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.SUCCESS);
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 买家订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        OrderDto orderDto = new OrderDto();
        try {
             orderDto = orderService.findOneOrder(orderId);
        }catch (SellExcption e){
            log.error("【买家端订单详情】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDto",orderDto);
        return new  ModelAndView("order/detail",map);
    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        try {
            OrderDto orderDto = orderService.findOneOrder(orderId);
            orderService.finish(orderDto);
        }catch (SellExcption e){
            log.error("【卖家端完结订单】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.SUCCESS);
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }


}
