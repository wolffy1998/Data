package com.mango.service.client;

import com.mango.service.entity.User;
import org.springframework.stereotype.Component;

/**
 * @Author mango
 * @Since 2020/2/6 20:43
 **/
@Component
public class FallBack implements UserClient {

    @Override
    public User getUser(Integer id) {
        User user = new User();
        user.setUsername("服务正忙，请稍后重试");
        return user;
    }

}
