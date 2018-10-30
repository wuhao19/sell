package com.wuhao.sell.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wuhao.sell.util.DtaeTOLongSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目类
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    /** 类目id.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /** 类目名称.*/
    private String categoryName;

    /** 类目编号.*/
    private Integer categoryType;

    /** 创建时间.*/
    @JsonSerialize(using = DtaeTOLongSerializer.class)
    private Date createTime;

    /** 更新时间.*/
    @JsonSerialize(using = DtaeTOLongSerializer.class)
    private Date updateTime;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public ProductCategory() {

    }
}
