package com.wuhao.sell.enums;

/**
 * 订单状态的枚举
 */

public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
    ;
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
