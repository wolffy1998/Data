package cn.thorne5691.servlet;

import cn.thorne5691.servlet.http.HttpSession;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * 编程式注册servlet,filter,listen
 * @author thorne5691
 * @since 2019/9/11 15:10
 */
@HandlesTypes(HttpSession.class)
public class ServletContainerInitializer implements javax.servlet.ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("感兴趣的类型:");
        for(Class<?> cls:set) {
            System.out.println(cls);
        }

        ServletRegistration.Dynamic dynamic = servletContext.addServlet("myservlet", cn.thorne5691.servlet.ServletContext .class);
        dynamic.addMapping("/myservlet");
    }

}
