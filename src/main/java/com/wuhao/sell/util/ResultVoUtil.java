package com.wuhao.sell.util;

import com.wuhao.sell.VO.ResultVo;

/**
 * 构建的gson格式工具
 */
public class ResultVoUtil {
    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMessage("成功");
        resultVo.setData(object);
        return resultVo;
    }
    public static ResultVo success(){
        return success(null);
    }
    public static ResultVo error(Integer code,String message){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMessage(message);
        return resultVo;
    }
}
