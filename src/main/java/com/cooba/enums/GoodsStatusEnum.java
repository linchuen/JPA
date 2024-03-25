package com.cooba.enums;

import lombok.Getter;

@Getter
public enum GoodsStatusEnum {
    FAILED(0),
    SUCCEED(1);

    private final int type;

    GoodsStatusEnum(int type) {
        this.type = type;
    }
}
