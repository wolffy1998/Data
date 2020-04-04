package com.leyou.item.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author mango
 * @Since 2020/2/11 14:45
 **/
@Configuration
public class LeyouCosConfiguration {

    /**
     * 定制SpringMVC使用的的跨域过滤器
     * 跨域问题是针对ajax跨域访问的一种限制
     * 当跨域时会预前访问，服务器告诉浏览器允许跨域访问后在一段时间内不会在预前访问可以直接跨域访问
     * 预前访问也会拉到服务器的响应但是浏览器不会进行解析
     * 限制是浏览器加的，服务器可以解除这种限制
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        // 初始化Corsp配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许跨域的域名，如果要携带Cookie就不能写*,*代表任何域都可以跨域
        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
        // 允许携带Cookie
        corsConfiguration.setAllowCredentials(true);
        // *代表所有请求方法 GET POST PUT DELETE
        corsConfiguration.addAllowedMethod("*");
        // 允许携带任何头信息
        corsConfiguration.addAllowedHeader("*");
        // 配置允许跨域的时间
        corsConfiguration.setMaxAge(3600L);
        // 初始化Cors配置源
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
