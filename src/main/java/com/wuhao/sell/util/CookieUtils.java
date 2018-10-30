package com.wuhao.sell.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtils {
    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response,
                                   String name,
                                   String value,
                                   int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request,String name){
        Map<String, Cookie> cookieMap = readCookie(request);
        if (cookieMap != null){
            if(cookieMap.containsKey(name)){
                return cookieMap.get(name);
            }
        }
        return null;
    }

    private static Map<String,Cookie> readCookie(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie:cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
            return cookieMap;
        }
        return null;
    }
}
