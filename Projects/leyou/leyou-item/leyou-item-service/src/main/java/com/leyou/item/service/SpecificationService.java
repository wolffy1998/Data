package com.leyou.item.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author mango
 * @Since 2020/2/15 13:58
 **/
@Service
public class SpecificationService {

    @Autowired
    SpecGroupMapper specGroupMapper;

    @Autowired
    SpecParamMapper specParamMapper;

    public List<SpecGroup> queryGroupsByCid(Long cid) {
        QueryWrapper<SpecGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", cid);
        return this.specGroupMapper.selectList(queryWrapper);
    }


    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean search) {
        QueryWrapper<SpecParam> queryWrapper = new QueryWrapper<>();
        if (gid != null) {
            queryWrapper.eq("group_id", gid);
        }
        if (cid != null) {
            queryWrapper.eq("cid", cid);
        }
        if (generic != null) {
            queryWrapper.eq("generic", generic);
        }
        if (generic != search) {
            queryWrapper.eq("search", search);
        }
        return this.specParamMapper.selectList(queryWrapper);
    }

}
