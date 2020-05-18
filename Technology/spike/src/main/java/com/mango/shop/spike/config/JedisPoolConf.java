package com.mango.shop.spike.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author mango
 * @Since 2020/2/29 14:59
 **/
@Configuration
@EnableConfigurationProperties(JedisProperties.class)
public class JedisPoolConf {

    @Bean
    @ConfigurationProperties("jedis.pool.config")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean
    public JedisPool jedisPoolFactory(JedisProperties jedisProperties, JedisPoolConfig jedisPoolConfig) {
        return new JedisPool(jedisPoolConfig,jedisProperties.getUrl(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword());
    }



}
