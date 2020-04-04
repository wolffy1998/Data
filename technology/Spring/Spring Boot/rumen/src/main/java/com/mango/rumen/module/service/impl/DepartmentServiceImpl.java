package com.mango.rumen.module.service.impl;

import com.mango.rumen.module.entity.Department;
import com.mango.rumen.module.mapper.DepartmentMapper;
import com.mango.rumen.module.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mango
 * @since 2020-01-20
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
