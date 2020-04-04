package com.mango.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/**
 * @SpringCloudApplication包含以下注解
 * @SpringBootApplication
 * @EnableDiscoveryClient  发现Euraka服务
 * @EnableCircuitBreaker
 */
@SpringCloudApplication
@EnableFeignClients
public class MangoServiceConsumerApplication {

    /**
     * @LoadBalanced注解启动负载均衡
     * 调用远程服务可用服务名代替主机:端口
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(MangoServiceConsumerApplication.class, args);
    }

}
