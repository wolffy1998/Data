package com.leyou.item.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.common.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mango
 * @Since 2020/2/11 16:50
 **/
@Service
public class BrandService extends ServiceImpl<BrandMapper, Brand> {

    /**
     * 根据查询条件分页并排序查询品牌信息
     * 总结：每个传入或者返回的参数都要严格的判断使用！
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        Page<Brand> brandPage = new Page<>(page, rows);

        // 排序ORDER BY '字段','字段',... '升序:ASC||降序：DESC'
        // 排序必须要有排序字段
        if (sortBy != null && !"".equals(sortBy)) {
            List<OrderItem> orders = new ArrayList<>();
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(sortBy);
            // 默认时升序排列
            if (desc != null) {
                orderItem.setAsc(desc);
            }
            orders.add(orderItem);
            brandPage.setOrders(orders);
        }

        // 查询条件构造器
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        // 由于参数key为null，所以执行SQL查询时报错
        if (key != null && !"".equals(key)) {
            wrapper.like("name", "%" + key + "%");
        }

        IPage<Brand> brandIPage = this.baseMapper.selectPage(brandPage, wrapper);
        return new PageResult<Brand>(brandIPage.getTotal(),brandPage.getPages(), brandPage.getRecords());
    }

    /**
     * @Transactional启动事物
     * 如果插入失败则回滚不继续往下执行
     * 这里品牌和分类是多对多关系，额外使用一张表绑定关系
     * 如果是一对一可根据情况在一张表中关联另一种表
     * 如果是一对多在一这张表关联多这张表
     * @param brand
     */
    @Transactional
    public void addBrand(Brand brand, List<Long> cids) {

        this.baseMapper.insert(brand);

        cids.forEach(cid -> {
            this.baseMapper.insertCategoryAndBrand(cid, brand.getId());
        });

    }

    public List<Brand> queryBrandsByCid(Long cid) {

       return this.baseMapper.selectBrandsByCid(cid);

    }
}
