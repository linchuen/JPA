package com.cooba.enums;

import lombok.Getter;

@Getter
public enum ErrorEnum {
    INSUFFICIENT_BALANCE(1,"餘額不足"),
    NO_LOCK(2,"無法取得鎖"),
    USER_NOT_EXIST(3,"用戶不存在"),
    INSUFFICIENT_GOODS(4,"餘額不足"),
    EMPTY_STOCK(5,"沒有庫存"),
    CURRENCY_NOT_SUPPORT(6,"幣種不支援"),
    INTERNAL_ERROR(9999,"內部錯誤");

    private final int code;
    private final String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
