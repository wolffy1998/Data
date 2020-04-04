package com.mango.zuul.mangozuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author mango
 * @Since 2020/2/6 21:25
 **/
@Component
public class MyZuulFilter extends ZuulFilter {

    /**
     * 过滤器类型  pre：前置，route：路由，post：后置，error：发送异常
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 同类型过滤器执行顺序，越低越优先
     * @return
     */
    @Override
    public int filterOrder() {
        return 10;
    }

    /**
     * 是否执行过滤器
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 做鉴权判断
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取当前请求上下文
        RequestContext currentContext = RequestContext.getCurrentContext();

        // 获取当前请求
        HttpServletRequest request = currentContext.getRequest();

        // 获取请求参数
        String token = request.getParameter("token");

        if(StringUtils.isEmpty(token)) {
            // 鉴权失败，拦截请求不转发
            currentContext.setSendZuulResponse(false);
            // 设置响应状态码
            currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            // 设置响应提示
            currentContext.setResponseBody("request error");
        }
        return null;
    }
}
