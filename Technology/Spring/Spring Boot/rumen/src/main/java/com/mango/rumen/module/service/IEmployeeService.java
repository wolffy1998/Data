package com.mango.rumen.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mango.rumen.module.entity.Employee;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mango
 * @since 2020-01-20
 */
public interface IEmployeeService extends IService<Employee> {


    Employee getEmployee(Integer id);

    Employee saveEmployee(Employee employee);

    void deleteEmployee(Integer id);

    Employee getEmployees(Integer id) throws InterruptedException;

}
