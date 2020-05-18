package com.mango.shop.spike.vo;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author mango
 * @Since 2020/3/2 22:22
 **/
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PhoneValidator.class})
public @interface IsPhone {

    /**
     * 参数是否必传
     * @return
     */
    boolean required() default true;

    /**
     * 错误默认提示消息
     * @return
     */
    String message() default "手机号不合法";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
