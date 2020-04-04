package com.mango.shop.spike.redis;

/**
 * @Author mango
 * @Since 2020/2/29 19:18
 **/
public interface CacheKey {

    int getExpireSeconds();

    String getRealKey() throws RuntimeException;

}
