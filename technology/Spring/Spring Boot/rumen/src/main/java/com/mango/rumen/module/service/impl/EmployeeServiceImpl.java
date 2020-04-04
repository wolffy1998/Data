package com.mango.rumen.module.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mango.rumen.module.entity.Employee;
import com.mango.rumen.module.mapper.EmployeeMapper;
import com.mango.rumen.module.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mango
 * @since 2020-01-20
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "emp")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Cacheable(key = "#id")
    public Employee getEmployee(Integer id) {
        log.info("查询用户{}",id);
        Employee employee = this.baseMapper.getEmployee(id);
        return employee;
    }

    @CachePut(key = "#root.args[0].id")
    public Employee saveEmployee(Employee employee) {
        this.baseMapper.saveEmployee(employee);
        log.info("保存用户{}",employee.getId());
        return employee;
    }

    @CacheEvict(key = "#id", beforeInvocation = true)
    @Override
    public void deleteEmployee(Integer id) {
        this.baseMapper.deleteById(id);
    }

    @Async
    public Employee getEmployees(Integer id) throws InterruptedException {
        // 主线程睡眠3秒
        Thread.sleep(3000);
        Employee employee = this.baseMapper.getEmployee(id);
        return employee;
    }

    /**
     * 如果定时任务还没执行完，第二次定时任务又开始了
     * 那么我们需要把线程池数量设大点防止阻塞
     */
    // @Scheduled(cron = "0 * * * * 0-7")
    public void dd() throws Exception {
        Thread.sleep(5000);
        System.out.println("定时任务执行成功");
    }



}
