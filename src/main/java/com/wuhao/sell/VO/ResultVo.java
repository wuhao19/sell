package com.wuhao.sell.VO;

import lombok.Data;

/**
 * http请求返回最外层对象
 */
@Data
public class ResultVo<T> {
    /** 错误码 */
    private Integer code;
    /** 提示信息 */
    private String message;
    /** 返回的具体类容 */
    private T data;
}
