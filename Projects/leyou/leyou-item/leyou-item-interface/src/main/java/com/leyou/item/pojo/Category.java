package com.leyou.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author mango
 * @Since 2020/2/10 17:26
 **/
@Data
@TableName(value = "tb_category")
public class Category {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Long parentId;

    private Boolean isParent; // 注意isParent生成的getter和setter方法需要手动加上Is

    private Integer sort;

}
