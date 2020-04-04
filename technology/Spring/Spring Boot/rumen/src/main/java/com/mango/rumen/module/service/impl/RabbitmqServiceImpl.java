package com.mango.rumen.module.service.impl;

import com.mango.rumen.module.service.IRabbitmqService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Author mango
 * @Since 2020/1/31 19:29
 **/
@Service
public class RabbitmqServiceImpl implements IRabbitmqService {


    @RabbitListener(queues = "mango.direct")
    @Override
    public void receive(Message message) {
        System.out.println("接收到消息" + message.getBody());
        System.out.println("消息头" + message.getMessageProperties());
    }

}
