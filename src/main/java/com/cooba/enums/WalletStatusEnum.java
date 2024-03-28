package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WalletStatusEnum {
    FAILED(0),
    SUCCEED(1);

    private final int type;

    WalletStatusEnum(int type) {
        this.type = type;
    }

    private static final Map<Integer, WalletStatusEnum> enumMap = new HashMap<>();

    static {
        for (WalletStatusEnum value : WalletStatusEnum.values()) {
            enumMap.put(value.type, value);
        }
    }

    public static Optional<WalletStatusEnum> getEnum(int type) {
        return Optional.ofNullable(enumMap.get(type));
    }
}
