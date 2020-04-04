package com.mango.rumen.module.controller;


import com.mango.rumen.module.entity.Employee;
import com.mango.rumen.module.service.IEmployeeService;
import com.mango.rumen.module.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mango
 * @since 2020-01-20
 */
@RestController
public class EmployeeController {

    @Autowired
    IEmployeeService employeeService;

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping("/employee")
    public String saveEmployee(Employee employee) {
        return employeeService.saveEmployee(employee).getId().toString();
    }

    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.deleteEmployee(id);
        return "Success";
    }

    @GetMapping("testAsync")
    public String testAsync(Integer id) throws InterruptedException {
        System.out.println("开始异步测试");
        long start = new Date().getTime();
        employeeService.getEmployees(id);
        long end = new Date().getTime();
        System.out.println("结束异步测试,用时：" + (end-start));
        return "测试完成";
    }

    @GetMapping("/te")
    @ResponseBody
    public String test() {
        return "yes";
    }

}
