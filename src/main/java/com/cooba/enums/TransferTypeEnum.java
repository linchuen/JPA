package com.cooba.enums;

import lombok.Getter;

@Getter
public enum TransferTypeEnum {
    DEPOSIT(1),
    WITHDRAW(2);

    private final int type;

    TransferTypeEnum(int type) {
        this.type = type;
    }
}
