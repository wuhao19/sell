package com.wuhao.sell.util;

import com.wuhao.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class KeyUtil {
    /**
     * 生产唯一的主键
     * 格式：时间+随机数
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer a = random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(a);
    }

}
