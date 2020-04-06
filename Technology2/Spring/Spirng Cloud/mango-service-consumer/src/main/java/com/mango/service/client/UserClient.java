package com.mango.service.client;

import com.mango.service.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @FeignClient("service-provider") 指定服务的提供者id，application.name的名称
 * @Author mango
 * @Since 2020/2/5 21:03
 **/
@FeignClient(value = "service-provider", fallback = FallBack.class)
public interface UserClient {

    @GetMapping("/provider/user/{id}")
    public User getUser(@PathVariable("id") Integer id);

}
