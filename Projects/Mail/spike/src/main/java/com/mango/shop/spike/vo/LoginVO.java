package com.mango.shop.spike.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author mango
 * @Since 2020/3/2 13:04
 **/
@Data
public class LoginVO {

    @IsPhone(required = true)
    private String mobile;

    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 14, message = "密码必须在6到14为之间")
    private String password;

}
