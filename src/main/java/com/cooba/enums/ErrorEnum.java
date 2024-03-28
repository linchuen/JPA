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
    PARAMETER_NOT_VALID(7,"參數不服"),
    MERCHANT_NOT_EXIST(8,"商戶不存在"),
    ADMIN_NOT_EXIST(9,"管理者不存在"),
    GOODS_NOT_EXIST(10,"商品不存在"),
    INTERNAL_ERROR(9999,"內部錯誤");

    private final int code;
    private final String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
