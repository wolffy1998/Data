package com.leyou.item.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.common.PageResult;
import com.leyou.item.mapper.*;
import com.leyou.item.model.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.pojo.Stock;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author mango
 * @Since 2020/2/15 21:24
 **/
@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;



    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {
        QueryWrapper<Spu> queryWrapper = new QueryWrapper<>();
        // 搜索条件
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.like("title", key);
        }
        // mysql没有布尔类型，用短整型代替布尔型 0：false,1：true
        if (saleable != null) {
            queryWrapper.eq("saleable", saleable);
        }
        // 分页
        Page<Spu> page1 = new Page<>(page, rows);

        Page<Spu> spuPage = this.spuMapper.selectPage(page1, queryWrapper);

        List<Spu> spuList = spuPage.getRecords();

        List<SpuBo> spuBos = new ArrayList<>();

        spuList.forEach(spu ->{
            SpuBo spuBo = new SpuBo();
            // copy共同属性的值到新的对象
            BeanUtils.copyProperties(spu, spuBo);

            // 查询分类名称
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            // StringUtils.join(names, "/")把集合转化为String,用/分割
            spuBo.setCname(StringUtils.join(names, "/"));

            // 查询品牌的名称
            spuBo.setBname(this.brandMapper.selectById(spu.getBrandId()).getName());

            spuBos.add(spuBo);
        });

        return new PageResult<SpuBo>(spuPage.getTotal(), spuBos);


    }

    /**
     * 新增商品
     * @param spuBo
     */
    @Transactional
    public void saveGoods(SpuBo spuBo) {
        // 防止误差主键 -- 细节
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        // mybatis-plus配置了插入更新自动填充时间
        // 这里插入后返回主键到spuBo
        this.spuMapper.insert(spuBo);

        SpuDetail spuDetail = spuBo.getSpuDetail();
        // 因为详情表和商品表本质是一张表，所以公用主键
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insert(spuDetail);

        saveSkuAndStock(spuBo);
    }

    private void saveSkuAndStock(SpuBo spuBo) {
        // 循环插入sku
        spuBo.getSkus().forEach(sku -> {
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            skuMapper.insert(sku);

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });
    }



    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        QueryWrapper<SpuDetail> spuDetailQueryWrapper = new QueryWrapper<>();
        spuDetailQueryWrapper.eq("spu_id", spuId);
        return this.spuDetailMapper.selectOne(spuDetailQueryWrapper);
    }

    /**
     * 根据spuid查询sku
     * @param id
     * @returnCtrl
     */
    public List<Sku> querySkusBySpuId(Long id) {
        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.eq("spu_id", id);
        List<Sku> skuList = this.skuMapper.selectList(skuQueryWrapper);
        skuList.forEach(sku -> {
            QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper<>();
            stockQueryWrapper.eq("sku_id", sku.getId());
            Stock stock = this.stockMapper.selectOne(stockQueryWrapper);
            sku.setStock(stock.getStock());
        });
        return skuList;
    }
}
