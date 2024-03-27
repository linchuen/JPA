package com.cooba.controller;

import com.cooba.enums.ErrorEnum;
import com.cooba.exception.BaseException;
import com.cooba.response.FailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public FailResponse<?> handle(BaseException e) {
        log.error(e.getMessage());
        return new FailResponse<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public FailResponse<?> handleException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return new FailResponse<>(ErrorEnum.PARAMETER_NOT_VALID.getCode(), ErrorEnum.PARAMETER_NOT_VALID.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public FailResponse<?> handleException(Exception e) {
        log.error(e.getMessage());
        return new FailResponse<>(ErrorEnum.INTERNAL_ERROR.getCode(), ErrorEnum.INTERNAL_ERROR.getMessage());
    }
}
