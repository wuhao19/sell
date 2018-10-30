package com.wuhao.sell.excption;

import com.wuhao.sell.enums.ResultEnum;

public class SellExcption extends RuntimeException {
    private Integer code;

    public SellExcption(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellExcption(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public SellExcption() {
    }
}
