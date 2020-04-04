package cn.thorne5691.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * 玩转ServletContext
 *
 * @Author thor5691
 * @since 2019/8/24 16:30
 */
@WebServlet(name = "servlerContext", urlPatterns = "/servletContext", initParams = { @WebInitParam(name = "noLoginPaths", value = "index.jsp;fail.jsp;/LoginServlet")})
public class ServletContext extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 通过ServletContext（Servlet上下文）获取Servlet视图
        System.out.println("缓冲区大小：" + resp.getBufferSize());
        javax.servlet.ServletContext servletContext = this.getServletContext();
        System.out.println(servletContext);
        ServletRegistration servletRegistration = servletContext.getServletRegistration("servlerContext");
        Collection<String> collection = servletRegistration.getMappings();
        java.util.Iterator iterable = collection.iterator();
        while (iterable.hasNext()) {
            String someone = (String)iterable.next();
            resp.getWriter().println(someone);
        }
        System.out.println("缓冲区大小1：" + resp.getBufferSize());
        // 获取Servlet初始化参数

        ServletConfig servletConfig = getServletConfig();
        Enumeration<String> initParameter = servletConfig.getInitParameterNames();
        while (initParameter.hasMoreElements()) {
            resp.getWriter().println(initParameter.nextElement());
        }

    }
}
