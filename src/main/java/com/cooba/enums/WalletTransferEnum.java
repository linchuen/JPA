package com.cooba.enums;

import lombok.Getter;

@Getter
public enum WalletTransferEnum {
    DEPOSIT(1),
    WITHDRAW(2);

    private final int type;

    WalletTransferEnum(int type) {
        this.type = type;
    }
}
