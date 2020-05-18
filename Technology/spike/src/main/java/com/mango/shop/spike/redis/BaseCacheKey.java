package com.mango.shop.spike.redis;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author mango
 * @Since 2020/2/29 19:20
 **/
public abstract class BaseCacheKey implements CacheKey {

    private int expireSeconds;

    private String key;

    public BaseCacheKey(String key) {
        this(0 ,key);
    }

    public BaseCacheKey(int expireSeconds, String key) {
        this.expireSeconds = expireSeconds;
        this.key = key;
    }

    public int getExpireSeconds() {
        return this.expireSeconds;
    }

    @Override
    public String getRealKey() throws RuntimeException {
        if (StringUtils.isBlank(this.key)) {
            throw new RuntimeException();
        }
        String simpleName = this.getClass().getSimpleName();
        return simpleName + ":" + key;
    }

}
