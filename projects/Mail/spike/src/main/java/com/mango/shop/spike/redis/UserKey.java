package com.mango.shop.spike.redis;

/**
 * @Author mango
 * @Since 2020/2/29 19:42
 **/
public class UserKey extends BaseCacheKey {

    public UserKey(String key) {
        super(key);
    }

    public UserKey(int expireSeconds, String key) {
        super(expireSeconds, key);
    }
}
