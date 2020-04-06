package com.mango.eureka.mangoeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //启用Eureka服务器
public class MangoEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangoEurekaApplication.class, args);
    }

}
