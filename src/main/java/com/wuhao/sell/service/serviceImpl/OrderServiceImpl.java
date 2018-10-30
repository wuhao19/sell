package com.wuhao.sell.service.serviceImpl;

import com.wuhao.sell.domain.OrderDetail;
import com.wuhao.sell.domain.OrderMaster;
import com.wuhao.sell.domain.ProductInfo;
import com.wuhao.sell.dto.CartDto;
import com.wuhao.sell.dto.OrderDto;
import com.wuhao.sell.enums.OrderStatusEnum;
import com.wuhao.sell.enums.PayEnum;
import com.wuhao.sell.enums.ResultEnum;
import com.wuhao.sell.excption.SellExcption;
import com.wuhao.sell.repository.OrderDetailRepository;
import com.wuhao.sell.repository.OrderMasterRepository;
import com.wuhao.sell.service.OrderService;
import com.wuhao.sell.service.ProductService;
import com.wuhao.sell.util.KeyUtil;
import com.wuhao.sell.util.OrderMasterTOOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private WebSocket webSocket;

    /**
     * 新建一个订单
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        List<CartDto> cartDtoList = new ArrayList<>();
        //查询商品（数量，价格）
        for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellExcption(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            //订单详情入库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
            //订单详情的商品id和数量加入cartDTO
            CartDto cartDtoTemp = new CartDto();
            cartDtoTemp.setProductId(orderDetail.getProductId());
            cartDtoTemp.setProductQuantity(orderDetail.getProductQuantity());
            cartDtoList.add(cartDtoTemp);
        }
        //写入数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterRepository.save(orderMaster);
        //下单成功扣库存
        productService.decreaserStock(cartDtoList);

        webSocket.sendMessage(orderDto.getOrderId());

        return orderDto;
    }

    /**
     * 查找一个订单
     * @param orderId
     * @return
     */
    @Override
    public OrderDto findOneOrder(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOrderMasterByOrderId(orderId);
        if (orderMaster == null) {
            throw new SellExcption(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (0 == orderDetailList.size()) {
            throw new SellExcption(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    /**
     * 查找一个用户的所有订单
     * @param orderOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDto> findList(String orderOpenid, Pageable pageable) {
        Page<OrderMaster> orderPage = orderMasterRepository.findByOrderOpenid(orderOpenid, pageable);
        //转换器转换类型
        List<OrderDto> orderDtoList = OrderMasterTOOrderDto.convet(orderPage.getContent());
        Page<OrderDto> orderDtoPage = new PageImpl<OrderDto>(orderDtoList, pageable, orderPage.getTotalElements());
        return orderDtoPage;
    }

    /**
     * 取消订单
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【取消订单】需要取消订单的状态不正确 orderId={},orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellExcption(ResultEnum.ORDERSTATUS_ERROR);
        }
        //修改订单状态取消
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updataResult = orderMasterRepository.save(orderMaster);
        if (updataResult == null) {
            log.info("【取消订单】更新失败 orderMaster={}", orderMaster);
            throw new SellExcption(ResultEnum.ORDER_STATUS_UPDATA_ERROR);
        }
        //返还库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            log.info("【取消订单】订单中无商品 orderDto={}", orderDto);
            throw new SellExcption(ResultEnum.ORDER_NOT_PRODUCT);
        }
        List<CartDto> cartDtoList = new ArrayList<>();
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            CartDto cartDto = new CartDto();
            BeanUtils.copyProperties(orderDetail, cartDto);
            cartDtoList.add(cartDto);
        }
        productService.increaseStock(cartDtoList);

        //如果已经支付需要退款
        if(orderDto.getPayStatus().equals(PayEnum.SUCCESS.getCode())){
            //TODO 支付退款
        }
        return orderDto;
    }

    /**
     * 完结订单
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("【订单完结】该订单的状态不能被完结 orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellExcption(ResultEnum.ORDER_FINISH_ERROR);
        }
        //修改状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster master = orderMasterRepository.save(orderMaster);
        if (!master.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())){
            log.info("【订单完结】订单状态修改失败了！orderMaster={}",orderMaster);
            throw new SellExcption(ResultEnum.ORDER_FINISH_UPDATE_ERROR);
        }
        return orderDto;
    }

    /**
     * 支付订单
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDto pay(OrderDto orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("【订单支付成功】该订单的状态不能被支付 orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellExcption(ResultEnum.ORDER_PAY_ERROR);
        }
        //判断支付状态
        if (!orderDto.getPayStatus().equals(PayEnum.WAIT.getCode())){
            log.info("【订单支付成功】该订单已经被支付了 orderDto={}",orderDto);
            throw new SellExcption(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDto.setPayStatus(PayEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result==null){
            log.info("【订单完结】订单支付失败了！result={}",result);
            throw new SellExcption(ResultEnum.ORDER_PAY_UPDATE_ERROR);
        }
        return orderDto;
    }

    /**
     * 查询所有订单列表
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDto> orderDtoList = OrderMasterTOOrderDto.convet(orderMasterPage.getContent());
        Page<OrderDto> orderDtoPage = new PageImpl<>(orderDtoList, pageable, orderMasterPage.getTotalElements());
        return orderDtoPage;
    }
}
