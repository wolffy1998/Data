package com.leyou.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author mango
 * @Since 2020/2/11 16:01
 **/
@Data
@TableName("tb_brand")
public class Brand {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;// 品牌名称

    private String image;// 品牌图片

    private Character letter;

}
