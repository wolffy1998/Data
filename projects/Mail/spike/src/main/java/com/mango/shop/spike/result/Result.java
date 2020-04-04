package com.mango.shop.spike.result;

import lombok.Data;

/**
 * @Author mango
 * @Since 2020/2/28 19:52
 **/
@Data
public class Result {

    private int code;

    private String message;

    private Object data;

    public Result() {

    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(CodeMessage cm) {
        this.code = cm.getCode();
        this.message = cm.getMessage();
    }
    /**
     * success与error方法返回的结果是没有指定泛型的Result
     * @return
     */
    public static Result succuss() {
        Result result = new Result(CodeMessage.SUCCESS);
        return result;
    }

    public static Result succuss(String message) {
        return new Result(200, message);
    }

    public static Result error() {
        Result result = new Result(CodeMessage.ERROR);
        return result;
    }

    public static Result error(String message) {
        return new Result(500, message);
    }

    /**
     *  这里返回的是指定了Result的泛型，T == data.class
     * @param data
     * @return
     */
    public Result build(Object data) {
        this.setData(data);
        return this;
    }

}
