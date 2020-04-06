package com.mango.service.controller;

import com.mango.service.entity.User;
import com.mango.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mango
 * @Since 2020/2/4 18:04
 **/
@RestController
@RequestMapping("/provider")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("user/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return this.userService.getUser(id);
    }

}
