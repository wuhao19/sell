package com.wuhao.sell.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wuhao.sell.enums.OrderStatusEnum;
import com.wuhao.sell.enums.PayEnum;
import com.wuhao.sell.util.DtaeTOLongSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 买家订单类
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster  {
    /** 订单id.*/
    @Id
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
}
