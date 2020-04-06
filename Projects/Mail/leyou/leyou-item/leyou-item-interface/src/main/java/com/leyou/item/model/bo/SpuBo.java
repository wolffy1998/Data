package com.leyou.item.model.bo;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import lombok.Data;

import java.util.List;

/**
 * 业务对象模型 POJO的一种类型
 * @Author mango
 * @Since 2020/2/15 21:40
 **/
@Data
public class SpuBo extends Spu {
    private String cname;// 商品分类名称

    private String bname;// 品牌名称

    SpuDetail spuDetail;// 商品详情

    List<Sku> skus;// sku列表

}
