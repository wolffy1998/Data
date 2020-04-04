package com.mango.rumen.module.service;

import org.springframework.amqp.core.Message;
import org.springframework.context.annotation.ImportResource;

/**
 * @Author mango
 * @Since 2020/1/31 19:28
 **/
@ImportResource
public interface IRabbitmqService {

    public void receive(Message message) ;
}
