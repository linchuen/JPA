package com.cooba.exception;

import com.cooba.enums.ErrorEnum;

public class InsufficientBalanceException extends BaseException {
    public InsufficientBalanceException() {
        super(ErrorEnum.INSUFFICIENT_BALANCE);
    }
}
