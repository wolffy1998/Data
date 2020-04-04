package com.leyou.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author mango
 * @Since 2020/2/15 13:51
 **/
@Data
@TableName(value = "tb_spec_group")
public class SpecGroup {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long cid;

    private String name;

    /**
     * 使用@TableFieId标识非数据库字段
     */
    @TableField(exist = false)
    private List<SpecParam> params;


}
