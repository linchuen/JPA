package com.cooba.exception;

import com.cooba.enums.ErrorEnum;

public class InsufficientGoodsException extends BaseException {
    public InsufficientGoodsException() {
        super(ErrorEnum.INSUFFICIENT_GOODS);
    }
}
