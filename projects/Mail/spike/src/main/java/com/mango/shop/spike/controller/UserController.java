package com.mango.shop.spike.controller;

import com.mango.shop.spike.entity.User;
import com.mango.shop.spike.result.Result;
import com.mango.shop.spike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mango
 * @Since 2020/2/28 21:29
 **/
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Restful要使用POST方法
     * 这里方便测试
     * @return
     */
    @GetMapping()
    public Result saveUser(User user) {
        if (user == null)
            return Result.error();
        this.userService.saveUser(user);
        // 如果插入失败会报错不会执行到这里来
        return Result.succuss();
    }

    @GetMapping("{id}")
    public Result getUser(@PathVariable("id") Integer id){
        // id已经校验
        User user = this.userService.getUser(id);
        return Result.succuss().build(user);
    }

}
