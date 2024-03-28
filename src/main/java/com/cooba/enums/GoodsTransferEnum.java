package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum GoodsTransferEnum {
    RESTOCK(1),
    SALE(2);

    private final int type;

    GoodsTransferEnum(int type) {
        this.type = type;
    }

    private static final Map<Integer, GoodsTransferEnum> enumMap = new HashMap<>();

    static {
        for (GoodsTransferEnum value : GoodsTransferEnum.values()) {
            enumMap.put(value.type, value);
        }
    }

    public static Optional<GoodsTransferEnum> getEnum(int type) {
        return Optional.ofNullable(enumMap.get(type));
    }
}
