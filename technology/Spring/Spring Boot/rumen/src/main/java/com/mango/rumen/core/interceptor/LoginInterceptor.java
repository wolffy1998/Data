package com.mango.rumen.core.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author mango
 * @Since 2020/1/15 20:26
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = (String)request.getSession().getAttribute("username");
//        if (StringUtils.isEmpty(token)) {
//            request.setAttribute("msg", "您无权访问请先登录");
//            // 我这里是post请求无权转发到get请求处理器
//            request.getRequestDispatcher("/index").forward(request, response);
//            log.info("拦截器正在执行请求处理前的拦截任务");
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
