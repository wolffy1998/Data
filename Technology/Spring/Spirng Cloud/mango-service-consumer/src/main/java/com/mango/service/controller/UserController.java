package com.mango.service.controller;

import com.mango.service.client.UserClient;
import com.mango.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mango
 * @Since 2020/2/4 18:16
 **/
@RestController
@RequestMapping("consumer")
public class UserController {

    /**
     * 这个地方idea报错不要管
     * 应该是注入id为userClient的对象，莫名报错！
     */
    @Autowired
    UserClient userClient;

    @GetMapping("user/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        User user = userClient.getUser(id);
        return user;
    }
}
