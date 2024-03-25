package com.cooba.enums;

import lombok.Getter;

@Getter
public enum WalletStatusEnum {
    FAILED(0),
    SUCCEED(1);

    private final int type;

    WalletStatusEnum(int type) {
        this.type = type;
    }
}
