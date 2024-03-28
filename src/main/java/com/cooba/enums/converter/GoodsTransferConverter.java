package com.cooba.enums.converter;

import com.cooba.enums.GoodsTransferEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GoodsTransferConverter implements AttributeConverter<GoodsTransferEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(GoodsTransferEnum attribute) {
        return attribute.getType();
    }

    @Override
    public GoodsTransferEnum convertToEntityAttribute(Integer dbData) {
        return GoodsTransferEnum.getEnum(dbData).orElse(null);
    }
}
