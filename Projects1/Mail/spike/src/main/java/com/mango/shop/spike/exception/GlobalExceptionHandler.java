package com.mango.shop.spike.exception;

import com.mango.shop.spike.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Spring MVC全局异常控制器
 * @Author mango
 * @Since 2020/3/2 21:23
 **/
@ControllerAdvice

public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value=Exception.class)
    public Result globalExceptionHandler(HttpServletRequest request, Exception e) {
        // 参数校验异常
        if (e instanceof BindException) {
            BindException exception = (BindException) e;
            List<ObjectError> allErrors = exception.getAllErrors();
            ObjectError objectError = allErrors.get(0);
            String defaultMessage = objectError.getDefaultMessage();
            return Result.error(defaultMessage);
        } else {
            return Result.error();
        }
    }


}
