package com.leyou.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 有了商品表为什么还要详情表？
 * 商品表和详情表其实就是一张表共用主键
 * 因为一般情况下我们查看商品表即可不需要用到这个数据量大的详情表，可以作到优化作用
 * 也就是说这个详情表和商品表的数据是同时生成
 * @Author mango
 * @Since 2020/2/15 21:30
 **/
@Data
@TableName("tb_spu_detail")
public class SpuDetail {

    private Long spuId;// 对应的SPU的id

    private String description;// 商品描述

    private String specialSpec;// 商品特殊规格的名称及可选值模板

    private String genericSpec;// 商品的全局规格属性

    private String packingList;// 包装清单

    private String afterService;// 售后服务
}
