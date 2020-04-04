package com.mango.shop.spike.config;

import com.mango.shop.spike.resolver.ArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author mango
 * @Since 2020/3/3 13:21
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    ArgumentResolver argumentResolver;

    /**
     * 把自定义的参数解析器注册给Spring MVC
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }

}
