package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WarehouseEnum {
    DEFAULT(1);

    private final int type;

    WarehouseEnum(int type) {
        this.type = type;
    }

    private static final Map<Integer, WarehouseEnum> enumMap = new HashMap<>();
    static {
        for (WarehouseEnum value : WarehouseEnum.values()) {
            enumMap.put(value.type,value);
        }
    }

    public static Optional<WarehouseEnum> getEnum(int type){
        return Optional.ofNullable(enumMap.get(type));
    }
}
