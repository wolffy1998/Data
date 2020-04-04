package com.leyou.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author mango
 * @Since 2020/2/8 15:06
 **/
@MapperScan(basePackages = "com.leyou.item.mapper")
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
// @EnableDiscoveryClient
public class ItemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemServiceApplication.class);
    }
}
