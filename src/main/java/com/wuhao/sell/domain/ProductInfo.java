package com.wuhao.sell.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wuhao.sell.enums.CodeEnum;
import com.wuhao.sell.enums.ProductStatusEnums;
import com.wuhao.sell.util.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class ProductInfo {
    /** 商品Id */
    @Id
    private String productId;

    /** 商品名称 */
    private String productName;

    /** 商品单价 */
    private BigDecimal productPrice;

    /** 商品库存 */
    private Integer productStock;

    /** 商品描述 */
    private String productDescription;

    /** 商品小图 */
    private String productIcon;

    /** 商品状态 0正常 1下架 默认为下架 */
    private Integer productStatus = ProductStatusEnums.DOWN.getCode();

    @JsonIgnore
    public ProductStatusEnums getProductStatusEnums(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnums.class);
    }

    /** 类目编号 */
    private Integer categoryType;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
