package com.mango.shop.spike.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @Author mango
 * @Since 2020/3/2 13:25
 **/
public class StringUtil {

    private StringUtil() {

    }

    /**
     * 生成4位数随机字符串
     */
    public static String randomString() {
        return RandomStringUtils.randomNumeric(4);
    }

}
