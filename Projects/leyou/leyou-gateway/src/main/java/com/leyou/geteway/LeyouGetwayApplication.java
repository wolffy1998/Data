package com.leyou.geteway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author mango
 * @Since 2020/2/8 14:47
 **/
@EnableZuulProxy
@SpringBootApplication
public class LeyouGetwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeyouGetwayApplication.class);
    }

}
