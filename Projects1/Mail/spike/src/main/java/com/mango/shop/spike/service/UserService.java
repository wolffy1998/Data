package com.mango.shop.spike.service;

import com.mango.shop.spike.entity.User;
import com.mango.shop.spike.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author mango
 * @Since 2020/2/28 21:24
 **/
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public int saveUser(User user) {
        return this.userMapper.saveUser(user);
    }

    public User getUser(Integer id){
        return this.userMapper.getUser(id);
    }

}
