package com.cooba.response;

public class SuccessResponse<T> extends BaseResponse<T>{

    public SuccessResponse(T data) {
        super(data);
        super.setCode(200);
    }
}
