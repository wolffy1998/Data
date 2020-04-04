package com.mango.shop.spike.entity;

/**
 * @Author mango
 * @Since 2020/3/4 16:20
 **/

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_user")
public class MiaoshaUser {

    private Integer id;

    private String phone;

    private String password;

    private String salt;

    @TableField("createtime")
    private Date createTime;

}
