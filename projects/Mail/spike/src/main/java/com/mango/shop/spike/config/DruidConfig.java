package com.mango.shop.spike.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author mango
 * @Since 2020/2/28 22:00
 **/
@Configuration
public class DruidConfig {

    /**
     * @ConfigurationProperties不仅仅标注在类似
     * 还可以标注在@Bean方法效果和前者一样
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.druid")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
}
