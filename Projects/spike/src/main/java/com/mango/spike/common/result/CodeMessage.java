package com.mango.spike.common.result;

import lombok.Data;

/**
 * @Author mango
 * @Since 2020/2/28 20:02
 **/
public enum CodeMessage {

    SUCCESS(200, "success"),

    ERROR(500, "server error"),

    ERROR_PARAM_EMPTY(501, "parameter is empty"),

    ERROR_FORMAT_PARAM(502, "parameter is error format");

    private int code;

    private String message;


    CodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
