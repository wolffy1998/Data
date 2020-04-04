package com.mango.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// 注解属性如果是数组传入单个值时可省去{}
@EnableDiscoveryClient //@EnableEurekaClient也可以启动推荐使用前者
@MapperScan(basePackages = {"com.mango.service.mapper"})
public class MangoServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangoServiceProviderApplication.class, args);
    }

}
