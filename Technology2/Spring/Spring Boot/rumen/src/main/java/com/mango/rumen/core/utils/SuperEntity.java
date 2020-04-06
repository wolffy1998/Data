package com.mango.rumen.core.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author mango
 * @Since 2020/1/20 14:30
 **/
public class SuperEntity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;

    @TableField(value = "createtime", fill = FieldFill.INSERT_UPDATE)
    protected Date createtime;

    @TableField(value = "updatetime", fill = FieldFill.INSERT_UPDATE)
    protected Date updatetime;

    @TableField(value = "deletetime", fill = FieldFill.INSERT_UPDATE)
    protected Date deletetime;
}
