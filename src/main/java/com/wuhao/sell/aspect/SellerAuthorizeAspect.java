package com.wuhao.sell.aspect;

import com.wuhao.sell.excption.SellerAuthorizeExcption;
import com.wuhao.sell.util.CookieUtils;
import com.wuhao.sell.util.TokenAndRedisAndCookie;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.wuhao.sell.controlle.Seller*.*(..))"+
    "&& !execution(public * com.wuhao.sell.controlle.SellerController.*(..))")
    public void verify(){}



    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //查询cookie
        Cookie cookie = CookieUtils.getCookie(request, TokenAndRedisAndCookie.COOKIE_TOKEN);
        if(cookie==null){
            log.warn("[登录效验]：cookie中查不到token");
            throw new SellerAuthorizeExcption();
        }

        //查询redis
        String tokenVaule = redisTemplate.opsForValue().get(String.format(TokenAndRedisAndCookie.REDIS_TOKEN, cookie.getValue()));
        if (tokenVaule ==null){
            log.warn("[登录效验]：redis中查不到token");
            throw new SellerAuthorizeExcption();
        }
    }
}
