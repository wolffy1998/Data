package com.mango.shop.spike.utils;

import java.util.UUID;

/**
 * @Author mango
 * @Since 2020/3/3 13:02
 **/
public class UUIDUtils {

    private UUIDUtils() {

    }

    /**
     * 生成token
     * @return
     */
    public static String token(){
        return UUID.randomUUID().toString().replaceAll("_", "");
    }
}
