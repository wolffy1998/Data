package cn.thorne5691.servlet.http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 玩转HttpSession
 *
 * @author thorne5691
 * @since 2019/9/17 11:09
 */
@WebServlet(urlPatterns = "/session")
public class HttpSession extends HttpServletRequest {

    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Question：会话什么时候被创建
        // Answer：会话是通过请求对象调用getSession()方法创建的。如果存在与请求关联的会话调用getSession()返回该会话，没有则创建一个与请求关联的会话。
        // Question：客户端加入会话的流程
        // Answer：客户端向服务器发送请求后，如果服务器通过请求对象创建会话则会返回给客户端Cookie，客户端再次请求时携带此Cookie即可加入会话
        javax.servlet.http.HttpSession session = req.getSession(true);
        resp.setContentType("text/html;charset=utf8");
        // 设置session最大空闲时间间隔--当达到最大空闲时间后会调用invalidate()方法销毁session
        session.setMaxInactiveInterval(60 * 1000);
        session.setAttribute("test", "生活毫无压力那还有什么意思！");
    }

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        javax.servlet.http.HttpSession session = req.getSession();
        resp.setContentType("text/html;charset=utf8");
        System.out.println("会话是否为新的：" + session.isNew());
        System.out.println("会话：" + session.getId());
        resp.getWriter().print(session.getAttribute("test"));
    }

}
