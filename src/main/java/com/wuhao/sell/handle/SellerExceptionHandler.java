package com.wuhao.sell.handle;

import com.wuhao.sell.excption.SellerAuthorizeExcption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {
    @Value("projectUrl.sell")
    private String sell;
    //http://localhost:8080/sell/seller/product/projectUrlsell/seller/index
    @ExceptionHandler(value = SellerAuthorizeExcption.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:http://wuhao191919.nat300.top/sell/seller/index");
    }
}
