package com.leyou.item.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @Author mango
 * @Since 2020/2/20 16:43
 **/
@Data
@TableName("tb_sku")
public class Sku {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long spuId;
    private String title;
    private String images;
    private Long price;
    private String ownSpec;// 商品特殊规格的键值对
    private String indexes;// 商品特殊规格的下标
    private Boolean enable;// 是否有效，逻辑删除用
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;// 创建时间
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;// 最后修改时间
    @TableField(exist = false)
    private Integer stock;// 库存

}
