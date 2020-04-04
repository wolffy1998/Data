package com.mango.rumen.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author mango
 * @Since 2020/1/31 13:31
 **/
@Configuration
@MapperScan(basePackages = "com.mango.rumen.module.mapper")
public class MPConfig {

}
