package cn.thorne5691.servlet.http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;

/**
 * 玩转ServletResponse
 *
 * @Author thor5691
 * @since 2019/8/24 16:30
 */
@WebServlet(urlPatterns = "/httpServletResponse")
public class HttpServletResponse extends HttpServlet {

    /**
     * Question1：请求头部采用什么字符编码？
     * Answer：字符编码不会影响到头部的编码。待深入tomcat服务器研究。
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setHeader("Data","中文");
        resp.getWriter().print("你好世界");
    }

    // Buffer
    @Override
    @SuppressWarnings({"dsf", "dsfs", "dfsdf"})
    @Deprecated
    protected void doPost(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        // Content-Type头必须设置在响应刷到缓冲区之前设置
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        resp.setBufferSize(65536);
        PrintWriter printWriter = resp.getWriter();
        printWriter.print("Hellow World");
        System.out.print("是否被提交：" + resp.isCommitted());
        //强制刷出缓存区内容到客户端
        if (resp.isCommitted()) {
            throw new IllegalStateException();
        }
        // flushBuffer强制刷出缓存区数据到客户端，如果已经提交则抛出IllegalStateException异常
        resp.flushBuffer();
        System.out.print("缓冲区大小：" + resp.getBufferSize());
        //当响应没有提交时，reset 方法清空缓冲区的数据。头信息，状态码。之前 servlet 调用 getWriter 或 getOutputStream 设置的状态会被清空
        // 如果响应没有被提交，resetBuffer 方法将清空缓冲区中的内容，但不清空请求头和状态码。
        resp.reset();
        resp.resetBuffer();
        printWriter.print("世界你好<br>换行");
    }

    // Header
    @Override
    protected void doHead(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("X-Powered-By", " Servlet/3.1 JSP/2.2 (GlassFish Open Source Edition 4.0 Java/Oracle Corporation/1.7) ");
        resp.setDateHeader("Now", System.currentTimeMillis());
        // addHeader可以设置多个同名称的头，而setHeader只能设置一个同名称的头多次设置会覆盖
        resp.setIntHeader("Number", 100);
        resp.addIntHeader("Number", 200);
        System.out.print(resp.getHeader("Now") + "<br>");
        System.out.print(resp.getHeaders("Number"));
    }

    // Locale
    @Override
    protected void doPut(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        // http情况下以下等于resp.setHead("Content-Language","en")
        resp.setLocale(Locale.ENGLISH);
        resp.getWriter().print("你好世界");
    }

    // Easy Method
    @Override
    protected void doDelete(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        // sendError 方法将设置适当的 header 和内容体用于返回给客户端返回错误消息
        // resp.sendError(500,"内容以转移！");
        // sendRedirect 方法将设置适当的 header 和内容体将客户端重定向到另一个地址
        resp.sendRedirect(req.getContextPath() + "/test");
    }
}
