package com.wuhao.sell.form;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wuhao.sell.util.DtaeTOLongSerializer;
import lombok.Data;
import java.util.Date;

@Data
public class CategoryForm {
    /** 类目id.*/
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
}
