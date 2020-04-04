package com.mango.service.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mango.service.entity.User;
import com.mango.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @Author mango
 * @Since 2020/2/4 18:06
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public User getUser(Integer id) {
        return this.baseMapper.selectById(id);
    }

}
