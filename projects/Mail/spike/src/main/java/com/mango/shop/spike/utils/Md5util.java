package com.mango.shop.spike.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author mango
 * @Since 2020/3/1 17:09
 **/
@Slf4j
public class Md5util {

    private Md5util() {

    }

    /**
     * 加盐加密
     * @param password
     * @param salt
     * @return
     */
    public static String encryption(String password, String salt) throws Exception {
        if (StringUtils.isBlank(password) || StringUtils.isBlank(salt)) {
            throw new Exception();
        }
        String saltPassword = password + salt;
        System.out.println("加盐密码：" + saltPassword);
        String encryption = DigestUtils.md2Hex(saltPassword);
        return encryption;
    }



}
