package com.mango.rumen.module.controller;

import com.mango.rumen.module.dao.DepartmentDao;
import com.mango.rumen.module.dao.EmployeeDao;
import com.mango.rumen.module.model.Department;
import com.mango.rumen.module.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author mango
 * @Since 2020/1/15 16:01
 **/
@Controller
public class LoginController {

    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = {"/","login","index"})
    public String index() {
        return "index";
    }

    /**
     * @RequestParam 添加了这个注册如果不传对应的参数则会报错
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/mainInfo")
    public String mainInfo(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       Model model, HttpSession session) {
        if (!StringUtils.isEmpty(username) && "123456".equals(password)) {
            session.setAttribute("username", username);
            // 使用重定向可以防止表单重复提交
            // 重定向会重新发送请求意味着重新生产request,response
            return "redirect:/main";
        }
        model.addAttribute("msg", "用户名密码错误");
        return "index";
    }

    @GetMapping("/emps")
    public String emps(Model model) {
        Collection collection = employeeDao.getAll();
        model.addAttribute("emps", collection);
        return "/emp/list";
    }

    @GetMapping("/emp")
    public String emp(Model model) {
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        return "/emp/add";
    }

    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        employeeDao.save(employee);
        // 如果是返回"/emps"那么模板引擎会去解析classpath:/template + /emps.html
        // 如果是"redirect:/emps"那么会重新发生一个请求地址为当前项目路径下的/emps
        // 如果是"forward:/emps"那么会把当前请求转发请求地址为当前项目路径下的/emps
        // 可以查看源码ThymeleafViewResolver
        return "redirect:/emps";
    }

    /**
     * @PathVariable("id")把请求路径上的id值绑定到形参的id上
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/emp/{id}")
    public String editEmpPage(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        model.addAttribute("emp", employee);
        return "/emp/add";
    }

    @PutMapping("/emp")
    public String editEmp(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    @DeleteMapping("/emp/{id}")
    public String delEmp(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/emps";
    }

    @ResponseBody
    @GetMapping("/employee")
    public Object employee() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from employee");
        return maps.get(0);
    }

}
