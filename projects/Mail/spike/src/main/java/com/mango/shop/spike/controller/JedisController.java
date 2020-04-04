package com.mango.shop.spike.controller;

import com.mango.shop.spike.redis.UserKey;
import com.mango.shop.spike.result.Result;
import com.mango.shop.spike.service.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mango
 * @Since 2020/2/29 15:19
 **/
@RestController
public class JedisController {

    @Autowired
    JedisService jedisService;

    @GetMapping("/setValue")
    public Result setVale(String key, String value) {
        Boolean sign = jedisService.set(new UserKey(key), value);
        return Result.succuss().build(sign);
    }

    @GetMapping("/getValue")
    public Result getVale(String key) {
        String value = jedisService.get(new UserKey(key), String.class);
        return Result.succuss().build(value);

    }

}
