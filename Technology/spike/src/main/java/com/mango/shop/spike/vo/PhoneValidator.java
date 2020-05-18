package com.mango.shop.spike.vo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author mango
 * @Since 2020/3/2 22:24
 **/
public class PhoneValidator implements ConstraintValidator<IsPhone, String> {

    private boolean requeired;

    /**
     * @param constraintAnnotation 注解的属性信息
     */
    @Override
    public void initialize(IsPhone constraintAnnotation) {
        requeired = constraintAnnotation.required();
    }

    /**
     * 返回false
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (requeired) {
            return isPhone(value);
        } else {
            //phone 不是必须
            if (value == null || value.length() == 0) {
                return true;
            } else {
                return isPhone(value);
            }
        }
    }

    private boolean isPhone(String phone) {
        // 初始化一个Pattern对象
        Pattern phonePattern = Pattern.compile("1\\d{10}");
        if (phone == null || phone.length() <= 0) {
            return false;
        } else {
            // 初始化一个Matcher对象
            Matcher matcher = phonePattern.matcher(phone);
            return matcher.matches();
        }

    }
}
