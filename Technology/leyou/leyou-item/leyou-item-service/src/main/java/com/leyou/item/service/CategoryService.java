package com.leyou.item.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mango
 * @Since 2020/2/10 17:43
 **/
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {

    /**
     * 根据父节点查询子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", pid);
        List<Category> categories = this.baseMapper.selectList(queryWrapper);
        return  categories;
    }

    public List<String> queryNamesByIds(List<Long> asList) {

        List<Category> categories = this.baseMapper.selectBatchIds(asList);
        List<String> names = new ArrayList<>();
        for (Category category : categories) {
            names.add(category.getName());
        }
        return names;
    }
}
