package com.cooba.enums.converter;

import com.cooba.enums.WalletStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class WalletStatusConverter implements AttributeConverter<WalletStatusEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(WalletStatusEnum attribute) {
        return attribute.getType();
    }

    @Override
    public WalletStatusEnum convertToEntityAttribute(Integer dbData) {
        return WalletStatusEnum.getEnum(dbData).orElse(null);
    }
}
