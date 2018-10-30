package com.wuhao.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wuhao.sell.domain.OrderDetail;
import com.wuhao.sell.enums.OrderStatusEnum;
import com.wuhao.sell.enums.PayEnum;
import com.wuhao.sell.util.DtaeTOLongSerializer;
import com.wuhao.sell.util.EnumUtil;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//避免json返回类型有null的情况
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    /** 订单id.*/
    private String orderId;
    /** 买家名字.*/
    private String orderName;
    /** 买家手机.*/
    private String orderPhone;
    /** 买家地址.*/
    private String orderAddress;
    /** 买家微信id.*/
    private String orderOpenid;
    /** 订单总金额.*/
    private BigDecimal orderAmount;
    /** 订单状态. 默认0 为新订单 1 完结 2已经取消*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /** 支付状态.  默认 0未支付 1支付成功 2支付失败*/
    private Integer payStatus = PayEnum.WAIT.getCode();
    /** 创建时间.*/
    @JsonSerialize(using = DtaeTOLongSerializer.class)
    private Date createTime;
    /** 更新时间.*/
    @JsonSerialize(using = DtaeTOLongSerializer.class)
    private Date updateTime;
    /** 订单明细列表.*/
    List<OrderDetail> orderDetailList ;
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayEnum getPayEnum(){
        return EnumUtil.getByCode(payStatus,PayEnum.class);
    }
}
