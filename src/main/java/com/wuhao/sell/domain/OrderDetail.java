package com.wuhao.sell.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wuhao.sell.util.DtaeTOLongSerializer;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情类
 */
@Entity
@Data
public class OrderDetail {

    /** 订单详情id.*/
    @Id
    private String detailId;

    /** 订单id.*/
    private String orderId;

    /** 商品id.*/
    private String productId;

    /** 商品名称.*/
    private String productName;

    /** 商品单价.*/
    private BigDecimal productPrice;

    /** 商品数量.*/
    private Integer productQuantity;

    /** 商品小图.*/
    private String productIcon;

    /** 创建时间.*/
    @JsonSerialize(using = DtaeTOLongSerializer.class)
    private Date createTime;

    /** 更新时间.*/
    @JsonSerialize(using = DtaeTOLongSerializer.class)
    private Date updateTime;

}
