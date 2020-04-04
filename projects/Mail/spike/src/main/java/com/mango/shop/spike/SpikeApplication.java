package com.mango.shop.spike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @EnableTransactionManagement启用事物
 */
@SpringBootApplication
public class SpikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpikeApplication.class, args);
    }

}
