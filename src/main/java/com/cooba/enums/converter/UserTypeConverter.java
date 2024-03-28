package com.cooba.enums.converter;

import com.cooba.enums.UserTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserTypeConverter implements AttributeConverter<UserTypeEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserTypeEnum attribute) {
        return attribute.getType();
    }

    @Override
    public UserTypeEnum convertToEntityAttribute(Integer dbData) {
        return UserTypeEnum.getEnum(dbData).orElse(null);
    }
}
