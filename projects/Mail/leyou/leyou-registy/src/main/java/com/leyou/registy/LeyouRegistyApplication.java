package com.leyou.registy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author mango
 * @Since 2020/2/7 23:29
 **/
@SpringBootApplication
@EnableEurekaServer
public class LeyouRegistyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeyouRegistyApplication.class);
    }

}
