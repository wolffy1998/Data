package com.mango.shop.spike.resolver;

import com.mango.shop.spike.entity.MiaoshaUser;
import com.mango.shop.spike.service.JedisService;
import com.mango.shop.spike.service.LoginService;
import com.mango.shop.spike.vo.LoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author mango
 * @Since 2020/3/3 13:23
 **/
@Component
public class ArgumentResolver implements HandlerMethodArgumentResolver {


    @Autowired
    LoginService loginService;

    /**
     * 意思是@RequestMappings上的方法参数是否使用此解析器解析
     * 我们使用的条件是 ： 当@RequestMappings上的方法参数是LoginVO类型时使用此解析器
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(loginService.TOKEN);
        String cookieToken = getCookieValue(request, loginService.TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return loginService.getByToken(response, token);
    }

    /**
     * 从请求头中拿cokkiName
     * @param request
     * @param cookiName
     * @return
     */
    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
