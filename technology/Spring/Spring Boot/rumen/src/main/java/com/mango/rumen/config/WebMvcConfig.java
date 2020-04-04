package com.mango.rumen.config;

import com.mango.rumen.core.interceptor.LoginInterceptor;
import com.mango.rumen.core.resolver.MyLocaleResolver;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @Author mango
 * @Since 2020/1/15 17:31
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 添加视图映射
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("dashboard");
    }

    /**
     * 添加静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将所有/static/** 访问都映射到classpath:/static/ 目录下
        // registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }


    @Bean
    public MyLocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

    /**
     * 拦截器注册
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置不进行拦截的请求路径注意要加上/
        String filter = "/,/index,/login,/mainInfo";
        String[] split = filter.split(",");
        List<String> list = Arrays.asList(split);
        // 拦截器不会拦截静态资源
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(list);
    }

    /**
     * 启动Https的同时启动http
     * @return
     */
    // @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory =new TomcatServletWebServerFactory();
        Connector connector =new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(80);
        tomcatServletWebServerFactory.addAdditionalTomcatConnectors(connector);
        return tomcatServletWebServerFactory;

    }
}
