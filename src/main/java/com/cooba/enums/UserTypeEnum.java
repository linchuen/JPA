package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum UserTypeEnum {
    USER(1),
    ADMIN(2);

    private final int type;

    UserTypeEnum(int type) {
        this.type = type;
    }

    private static final Map<Integer, UserTypeEnum> enumMap = new HashMap<>();
    static {
        for (UserTypeEnum value : UserTypeEnum.values()) {
            enumMap.put(value.type,value);
        }
    }

    public static Optional<UserTypeEnum> getEnum(int type){
        return Optional.ofNullable(enumMap.get(type));
    }
}
