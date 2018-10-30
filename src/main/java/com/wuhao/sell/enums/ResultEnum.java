package com.wuhao.sell.enums;

public enum ResultEnum {
    SUCCESS(0,"操作成功"),
    ORDER_FOEM_ERROR(1,"表单参数不正确"),
    ORDER_CAR_ERROR(2,"购物车不能为空"),
    OPENID_NULL_ERROR(3,"openid不能为空"),
    ORDER_FIND_ERROR(4,"没有订单的操作权限"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单明细不存在"),
    ORDERSTATUS_ERROR(14,"订单状态不能被取消"),
    ORDER_STATUS_UPDATA_ERROR(15,"订单状态更新失败"),
    ORDER_NOT_PRODUCT(16,"订单中没有商品"),
    ORDER_FINISH_ERROR(17,"订单状态不能被完结"),
    ORDER_FINISH_UPDATE_ERROR(18,"订单完结状态更新失败"),
    ORDER_PAY_ERROR(19,"订单状态不能被支付"),
    ORDER_PAY_STATUS_ERROR(20,"该订单已经被支付过了"),
    ORDER_PAY_UPDATE_ERROR(21,"订单支付失败了"),
    PRODUCT_STATUS_ERROR(22,"商品在架状态不能被更改"),
    SELLER_NOT_EXIST(23,"卖家不存在"),
    LOGOUT(24,"登出成功！"),
    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
