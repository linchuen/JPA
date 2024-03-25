package com.cooba.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class BaseResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private Integer code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
