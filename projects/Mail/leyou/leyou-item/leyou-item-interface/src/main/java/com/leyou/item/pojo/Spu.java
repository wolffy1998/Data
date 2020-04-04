package com.leyou.item.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @Author mango
 * @Since 2020/2/15 21:14
 **/
@Data
@TableName("tb_spu")
public class Spu {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long brandId;
    private Long cid1;// 1级类目
    private Long cid2;// 2级类目
    private Long cid3;// 3级类目
    private String title;// 标题
    private String subTitle;// 子标题
    private Boolean saleable;// 是否上架
    private Boolean valid;// 是否有效，逻辑删除用
    /**
     * 插入一条数据时自动传递当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;// 创建时间
    /**
     * 测试mybatis-plus时间更新策略
     * 插入和更新时自动传递当前时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;// 最后修改时间

}
