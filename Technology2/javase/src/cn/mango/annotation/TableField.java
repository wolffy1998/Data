package cn.mango.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author mango
 * @Since 2020/3/9 17:03
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {

    int value() default 0;

    String name() default "";

    TypeEnum type() default TypeEnum.VARCHAR;

    /**
     * 这里只是为了演示注解类型元素
     * 实际上可以使用枚举数组实现约束信息
     * @return
     */
    Constraints constraints() default @Constraints;

}
