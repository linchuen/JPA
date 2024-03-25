package com.cooba.exception;

import com.cooba.enums.ErrorEnum;

public class UserNotExistException extends BaseException {
    public UserNotExistException() {
        super(ErrorEnum.USER_NOT_EXIST);
    }
}
