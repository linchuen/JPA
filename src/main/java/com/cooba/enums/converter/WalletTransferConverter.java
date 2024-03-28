package com.cooba.enums.converter;

import com.cooba.enums.WalletTransferEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class WalletTransferConverter implements AttributeConverter<WalletTransferEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(WalletTransferEnum attribute) {
        return attribute.getType();
    }

    @Override
    public WalletTransferEnum convertToEntityAttribute(Integer dbData) {
        return WalletTransferEnum.getEnum(dbData).orElse(null);
    }
}
