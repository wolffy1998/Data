package com.mango.shop.spike.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mango.shop.spike.entity.MiaoshaUser;
import com.mango.shop.spike.mapper.LoginMapper;
import com.mango.shop.spike.redis.UserKey;
import com.mango.shop.spike.result.CodeMessage;
import com.mango.shop.spike.utils.Md5util;
import com.mango.shop.spike.utils.UUIDUtils;
import com.mango.shop.spike.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author mango
 * @Since 2020/3/2 13:07
 **/
@Slf4j
@Service
public class LoginService extends ServiceImpl<LoginMapper, MiaoshaUser> {

    @Autowired
    JedisService jedisService;

    public static final String TOKEN = "token";

    /**
     * 正则验证登录手机号
     *
     * @param loginVO
     * @return
     */
    public CodeMessage checkLogin(HttpServletResponse response, LoginVO loginVO) throws Exception {
        // 先根据手机号拿出User
        QueryWrapper<MiaoshaUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", loginVO.getMobile());
        MiaoshaUser miaoshaUser = this.baseMapper.selectOne(queryWrapper);
        if (miaoshaUser == null) {
            // 这里要自定义异常，通过全局异常控制器捕获响应消息给客户端
            throw new Exception("用户名不存在");
        }
        String salt = miaoshaUser.getSalt();
        String encryption = Md5util.encryption(loginVO.getPassword(), salt);
        if (!miaoshaUser.getPassword().equals(encryption)) {
            throw new Exception("密码不正确");
        }
        String token = UUIDUtils.token();
        UserKey userKey = new UserKey(10000000, token);
        jedisService.set(userKey, miaoshaUser);
        Cookie cookie = new Cookie(LoginService.TOKEN, token);
        cookie.setMaxAge(userKey.getExpireSeconds());
        cookie.setPath("/");
        // response使用的是单实例
        response.addCookie(cookie);
        return CodeMessage.SUCCESS;
    }

    public Object getByToken(HttpServletResponse response, String token) {
        UserKey userKey = new UserKey(1000000, token);
        // 重新刷新一下cookie，redis
        // 让redish和cookie的过期时间为不活跃过期时间
        return jedisService.get(userKey, MiaoshaUser.class);

    }
}
