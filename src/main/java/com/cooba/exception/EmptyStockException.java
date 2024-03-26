package com.cooba.exception;

import com.cooba.enums.ErrorEnum;

public class EmptyStockException extends BaseException {
    public EmptyStockException() {
        super(ErrorEnum.EMPTY_STOCK);
    }
}
