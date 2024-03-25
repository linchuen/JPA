package com.cooba.enums;

import lombok.Getter;

@Getter
public enum GoodsTransferEnum {
    RESTOCK(1),
    SALE(2);

    private final int type;

    GoodsTransferEnum(int type) {
        this.type = type;
    }
}
