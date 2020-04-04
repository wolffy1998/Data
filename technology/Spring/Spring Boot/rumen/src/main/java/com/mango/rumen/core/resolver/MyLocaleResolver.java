package com.mango.rumen.core.resolver;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @ConditionalOnMissingBean 容器中如果有此类型的bean则不添加组件
 * @Author mango
 * @Since 2020/1/15 19:31
 **/
public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // 如果参数和头都没携带区域信息那么用系统默认
        Locale locale = Locale.getDefault();
        String param = request.getParameter("l");
        if (!StringUtils.isEmpty(param)) {
            String[] split = param.split("_");
            locale = new Locale(split[0], split[1]);
            return locale;
        }
        String header = request.getHeader("Content-Language");
        if (!StringUtils.isEmpty(header)) {
            String[] split = header.split("_");
            locale = new Locale(split[0], split[1]);
            return locale;
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
