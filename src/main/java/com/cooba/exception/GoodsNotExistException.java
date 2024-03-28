package com.cooba.exception;

import com.cooba.enums.ErrorEnum;

public class GoodsNotExistException extends BaseException {
    public GoodsNotExistException() {
        super(ErrorEnum.MERCHANT_NOT_EXIST);
    }
}
