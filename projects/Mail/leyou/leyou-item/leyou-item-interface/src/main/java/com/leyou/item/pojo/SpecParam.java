package com.leyou.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author mango
 * @Since 2020/2/15 13:52
 **/
@Data
@TableName(value = "tb_spec_param")
public class SpecParam {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long cid;

    /**
     * 数据表是对应字段是group_id
     * mybatis-plus默认配置tableUnderline为true时表示数据库使用下划线分割单词
     * mybatis-plus的ORM策略：小驼峰-下划线互转
     */
    private Long groupId;

    private String name;

    /**
     * numeric为MySQL关键字
     * 需要加ORM``标识为字段
     */
    @TableField(value = "`numeric`")
    private Boolean numeric;

    private String unit;

    private Boolean generic;

    private Boolean searching;

    private String segments;

}
