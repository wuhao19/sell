package com.wuhao.sell.controlle;

import com.wuhao.sell.VO.ResultVo;
import com.wuhao.sell.dto.OrderDto;
import com.wuhao.sell.enums.ResultEnum;
import com.wuhao.sell.excption.SellExcption;
import com.wuhao.sell.form.OrderForm;
import com.wuhao.sell.service.BuyerService;
import com.wuhao.sell.service.OrderService;
import com.wuhao.sell.util.OrderFormToOrderDto;
import com.wuhao.sell.util.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;
    //创建订单
    @PostMapping("/create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建表单】参数不正确，orderForm={}",orderForm);
            throw new SellExcption(ResultEnum.ORDER_FOEM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDto orderDto  = OrderFormToOrderDto.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空哦 ");
            throw new SellExcption(ResultEnum.ORDER_CAR_ERROR);
        }
        OrderDto result = orderService.createOrder(orderDto);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());



        return ResultVoUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVo<List<OrderDto>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page ,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size
                                         ){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw  new SellExcption(ResultEnum.OPENID_NULL_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDto> orderDtoPage = orderService.findList(openid, pageRequest);
        return ResultVoUtil.success(orderDtoPage.getContent());
    }
    //订单详情
    @GetMapping("/detail")
    public ResultVo<OrderDto> detail(@RequestParam String openid,
                                     @RequestParam String orderId
                                    ){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单详情】openid为空");
            throw  new SellExcption(ResultEnum.OPENID_NULL_ERROR);
        }
        //安全的订单操作
        OrderDto orderDto = buyerService.findOrderOne(openid, orderId);
        return ResultVoUtil.success(orderDto);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam String openid,
                           @RequestParam String orderId){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw  new SellExcption(ResultEnum.OPENID_NULL_ERROR);
        }
        //安全的订单操作
        buyerService.cancelOrder(openid, orderId);
        return ResultVoUtil.success();
    }


}
