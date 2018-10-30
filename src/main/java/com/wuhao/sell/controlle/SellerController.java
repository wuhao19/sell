package com.wuhao.sell.controlle;


import com.wuhao.sell.domain.SellerInfo;
import com.wuhao.sell.enums.ResultEnum;
import com.wuhao.sell.excption.SellExcption;
import com.wuhao.sell.form.SellerForm;
import com.wuhao.sell.service.SellerService;
import com.wuhao.sell.util.CookieUtils;

import com.wuhao.sell.util.TokenAndRedisAndCookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("seller/index")
@Slf4j
public class SellerController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("")
    public ModelAndView index(){
        return new ModelAndView("sell/index");
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid SellerForm form,
                              HttpServletResponse response,
                              Map<String,Object> map){
        SellerInfo sellerInfo = new SellerInfo();
        try {
             sellerInfo = sellerService.login(form.getSellerName(), form.getSellerPassword());
        }catch (SellExcption e){
            log.error("[买家登录错误]账号或者密码输入错误..");
            map.put("msg",e.getMessage());
            map.put("url","http://wuhao191919.nat300.top/sell/seller/index");
            return new ModelAndView("common/error",map);
        }
        //设置token到redis
        String token = UUID.randomUUID().toString();
        Integer expire = TokenAndRedisAndCookie.EXIORI;
        stringRedisTemplate.opsForValue().set(String.format(TokenAndRedisAndCookie.REDIS_TOKEN,token),
                sellerInfo.getSellerId(),expire, TimeUnit.SECONDS);
        //设置token到cookie
        CookieUtils.setCookie(response,TokenAndRedisAndCookie.COOKIE_TOKEN,token,expire);
        return new ModelAndView("redirect:http://wuhao191919.nat300.top/sell/seller/order/list");
    }


    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //cookie 查询
        Cookie cookie = CookieUtils.getCookie(request, TokenAndRedisAndCookie.COOKIE_TOKEN);
        if (cookie!=null){
            //清除redis
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(TokenAndRedisAndCookie.REDIS_TOKEN,cookie));
            //清除cookie
            CookieUtils.setCookie(response,TokenAndRedisAndCookie.COOKIE_TOKEN,null,0);
        }
        map.put("msg", ResultEnum.LOGOUT);
        map.put("url","http://wuhao191919.nat300.top/sell/seller/index");
        return new ModelAndView("common/success",map);
    }
}

