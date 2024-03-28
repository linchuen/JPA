package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WalletTransferEnum {
    DEPOSIT(1),
    WITHDRAW(2);

    private final int type;

    WalletTransferEnum(int type) {
        this.type = type;
    }

    private static final Map<Integer, WalletTransferEnum> enumMap = new HashMap<>();

    static {
        for (WalletTransferEnum value : WalletTransferEnum.values()) {
            enumMap.put(value.type, value);
        }
    }

    public static Optional<WalletTransferEnum> getEnum(int type) {
        return Optional.ofNullable(enumMap.get(type));
    }
}
