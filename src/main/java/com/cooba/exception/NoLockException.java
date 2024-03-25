package com.cooba.exception;

import com.cooba.enums.ErrorEnum;

public class NoLockException extends BaseException {
    public NoLockException() {
        super(ErrorEnum.NO_LOCK);
    }
}
