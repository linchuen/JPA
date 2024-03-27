package com.cooba.exception;

import com.cooba.enums.ErrorEnum;

public class AdminNotExistException extends BaseException {
    public AdminNotExistException() {
        super(ErrorEnum.USER_NOT_EXIST);
    }
}
