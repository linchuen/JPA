package com.cooba.exception;

import com.cooba.enums.ErrorEnum;

public class CurrencyNotSupportException extends BaseException {
    public CurrencyNotSupportException() {
        super(ErrorEnum.CURRENCY_NOT_SUPPORT);
    }
}
