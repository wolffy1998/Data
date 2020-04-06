package com.mango.shop.spike.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author mango
 * @Since 2020/2/29 16:03
 **/
@Data
@ConfigurationProperties(prefix = "jedis.pool")
public class JedisProperties {

    private String url;

    private Integer port;

    private Integer timeout;

    private String password;

}
