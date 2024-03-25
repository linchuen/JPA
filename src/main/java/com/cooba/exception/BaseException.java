package com.cooba.exception;

import com.cooba.enums.ErrorEnum;


public class BaseException extends RuntimeException{
    public BaseException(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }

    private final ErrorEnum errorEnum;
    private Integer code;
    private String message;

    public Integer getCode() {
        return this.errorEnum.getCode();
    }

    @Override
    public String getMessage() {
        return this.errorEnum.getMessage();
    }
}
