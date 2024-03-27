package com.cooba.exception;

import com.cooba.enums.ErrorEnum;

public class MerchantNotExistException extends BaseException {
    public MerchantNotExistException() {
        super(ErrorEnum.MERCHANT_NOT_EXIST);
    }
}
