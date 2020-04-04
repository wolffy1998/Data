package com.mango.rumen.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author mango
 * @Since 2020/1/31 19:23
 **/
@EnableRabbit
@Configuration
public class RabbitmqConfig {

    /**
     * 消息转换配置json序列化机制
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
