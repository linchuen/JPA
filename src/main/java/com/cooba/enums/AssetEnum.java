package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum AssetEnum {
    TWD(1),
    USD(2);

    private final int id;

    AssetEnum(int id) {
        this.id = id;
    }

    private static final Map<Integer, AssetEnum> enumMap = new HashMap<>();

    static {
        for (AssetEnum value : AssetEnum.values()) {
            enumMap.put(value.id, value);
        }
    }

    public static Optional<AssetEnum> getEnum(int id) {
        return Optional.ofNullable(enumMap.get(id));
    }
}
