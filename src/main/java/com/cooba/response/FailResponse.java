package com.cooba.response;

public class FailResponse<T> extends BaseResponse<T>{
    public FailResponse(Integer code, String message) {
        super(code, message);
    }
}
