package com.mango.spike.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author mango
 * @Since 2020/3/6 14:32
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean beanFilter = new FilterRegistrationBean();
        beanFilter.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        // 不需要监控的资源路径
        initParams.put("exclusions", "*.js,*.css,/druid/*");
        beanFilter.setInitParameters(initParams);
        beanFilter.setUrlPatterns(Arrays.asList("/*"));
        return beanFilter;
    }

    @Bean
    public ServletRegistrationBean setStatViewServlet() {
        ServletRegistrationBean beanServlet = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");
        initParams.put("allow", "");    /**默认就是允许所有访问*/
        beanServlet.setInitParameters(initParams);
        return beanServlet;
    }
}
