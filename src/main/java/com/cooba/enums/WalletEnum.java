package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WalletEnum {
    DEFAULT(1);

    private final int type;

    WalletEnum(int type) {
        this.type = type;
    }

    private static final Map<Integer, WalletEnum> enumMap = new HashMap<>();

    static {
        for (WalletEnum value : WalletEnum.values()) {
            enumMap.put(value.type, value);
        }
    }

    public static Optional<WalletEnum> getEnum(int type) {
        return Optional.ofNullable(enumMap.get(type));
    }
}
