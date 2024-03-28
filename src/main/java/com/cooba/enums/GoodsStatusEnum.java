package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum GoodsStatusEnum {
    FAILED(0),
    SUCCEED(1);

    private final int type;

    GoodsStatusEnum(int type) {
        this.type = type;
    }

    private static final Map<Integer, GoodsStatusEnum> enumMap = new HashMap<>();

    static {
        for (GoodsStatusEnum value : GoodsStatusEnum.values()) {
            enumMap.put(value.type, value);
        }
    }

    public static Optional<GoodsStatusEnum> getEnum(int type) {
        return Optional.ofNullable(enumMap.get(type));
    }
}
