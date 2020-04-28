package com.mango.spike.module.service.impl;

import com.mango.spike.module.entity.TbUser;
import com.mango.spike.module.mapper.TbUserMapper;
import com.mango.spike.module.service.ITbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mango
 * @since 2020-03-06
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements ITbUserService {

}
