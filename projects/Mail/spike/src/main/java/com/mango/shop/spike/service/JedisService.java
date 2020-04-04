package com.mango.shop.spike.service;

import com.alibaba.fastjson.JSON;
import com.mango.shop.spike.redis.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author mango
 * @Since 2020/2/29 15:16
 **/
@Slf4j
@Service
public class JedisService {

    @Autowired
    JedisPool jedisPool;

    /**
     * 把数据缓存在reids
     * @param cacheKey
     * @param param
     * @param <T>
     * @return
     */
    public <T> Boolean set(CacheKey cacheKey, T param) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String json = beanToString(param);
            if (json ==null || json.length() <= 0) {
                return false;
            }
            String realKey = cacheKey.getRealKey();
            if (cacheKey.getExpireSeconds() <= 0){
                jedis.set(realKey, json);
            }else {
                jedis.setex(realKey, cacheKey.getExpireSeconds(), json);
            }
            return true;

        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 从redis中获取数据
     * @param cacheKey
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(CacheKey cacheKey, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = cacheKey.getRealKey();
            String json = jedis.get(realKey);
            return StringToBean(json, clazz);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * json转bean
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T StringToBean(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(json);
        }
        if (clazz == long.class || clazz == Long.class) {
            return (T)Long.valueOf(json);
        }
        if (clazz == String.class) {
            return (T)json;
        }
        return JSON.toJavaObject(JSON.parseObject(json), clazz);
    }


    /**
     * bean转json
     * @param param
     * @param <T>
     * @return
     */
    private <T> String beanToString(T param) {
        Class<?> clazz = param.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return param + "";
        }
        if (clazz == long.class || clazz == Long.class) {
            return param + "";
        }
        if (clazz == String.class) {
            return (String)param;
        }
        return JSON.toJSONString(param);
    }

    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }

    /**
     * 判断key是否存在
     * @param cacheKey
     * @return
     */
    public Boolean exist(CacheKey cacheKey) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(cacheKey.getRealKey());
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * */
    public <T> Long incr(CacheKey cacheKey) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = cacheKey.getRealKey();
            return  jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * */
    public <T> Long decr(CacheKey cacheKey) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = cacheKey.getRealKey();
            return  jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }
}
