package cn.thorne5691.servlet.http;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * 玩转ServletRequest
 * MultipartConfig注解表示Servlet启动 multipart/form-data格式 数据的处理
 *
 * @Author thor5691
 * @since 2019/8/24 16:30
 */
@MultipartConfig
@WebServlet(urlPatterns = "/httpServletRequest")
public class HttpServletRequest extends HttpServlet {

    /**
     * HTTP protocol parameter
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int i = 0;
        int x = 0;
        // 获取客户端请求参数之前要设置字符编码
        req.setCharacterEncoding("UTF-8");

        System.out.println("参数a的值：" + req.getParameter("test"));

        // 获取参数名称枚举
        Enumeration<String> enumeration = req.getParameterNames();
        while (enumeration.hasMoreElements()) {
            System.out.println("第" + i++ + "个参数名为：" + enumeration.nextElement());
        }

        // 任何给定的参数的名称可存在多个参数值
        String[] values = req.getParameterValues("p");
        for (String value : values
        ) {
            System.out.println("第" + x++ + "个参数名为：" + value);
        }

        // 获取参数Map<String, String[]>集合
        Map<String, String[]> parameters = req.getParameterMap();
        System.out.println(parameters);
    }

    /**
     * HTTP protocol parameter
     * 文件上传
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int i = 0;

        System.out.println("表单的内容类型：" + req.getContentType());
        // 如果请求体的内容类型为：application/x-www-form-urlencoded
        if ("application/x-www-form-urlencoded".equals(req.getContentType())) {
            // Question1：Post请求是否可以从URI查询字符串填充参数？
            // Answer：可以
            req.setCharacterEncoding("UTF-8");
            // 以下是在 POST表单数据填充到参数集前必须满足的条件：
            // 1。该请求是一个 HTTP或 HTTPS请求。
            // 2。HTTP方法是 POST。
            // 3。内容类型是 application/x-www-form-urlencoded。
            // 4。该 servlet已经对 request对象的任意 getParameter方法进行了初始调用。
            // 如果不满足这些条件，而且参数集中不包括 POST表单数据，那么 servlet必须可以通过 request对象的输入 流得到 POST数据。
            // 如果满足这些条件，那么从 request 对象的输入流中直接读取 POST 数据将不再有效。
            System.out.print(req.getParameter("test"));
            InputStream inputStream = req.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            resp.getWriter().print(stringBuilder.toString());
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        // 如果请求体的内容类型为：multipart/form-data

        // 多部分 multipart/form-data 格式数据提交
        // 如果 servlet 的容器不提供 multi-part/form-data 格式数据的处理，这些数据将可通过 HttpServletReuqest.getInputStream 得到
        Collection<Part> parts = req.getParts();
        Iterator<Part> iterator = parts.iterator();
        while (iterator.hasNext()) {
            Part part = iterator.next();
            // Content-Disposition头有 part名称name="file"， 类容类型 form-data， 文件名称filename="eed9cbb76b32260bff0e5af2e76e7c6e0037aa0a_s2_n1.jpg"
            String disposition = part.getHeader("Content-Disposition");
            System.out.println("Content-Disposition：" + disposition);
            String filename = disposition.substring(disposition.indexOf("filename=\"") + 10,disposition.lastIndexOf("\""));
            String serverpath = this.getServletContext().getRealPath("upload");
            File file = new File(serverpath);
            Boolean signal = file.exists();
            if (!file.exists()) {
                file.mkdir();
            }
            InputStream inputStream = part.getInputStream();
            FileOutputStream fos = new FileOutputStream(serverpath + "/" + serverpath);
            byte[] bty = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(bty)) != -1) {
                fos.write(bty, 0, length);
            }
            fos.close();
            inputStream.close();
        }

    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(javax.servlet.http.HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }
}
