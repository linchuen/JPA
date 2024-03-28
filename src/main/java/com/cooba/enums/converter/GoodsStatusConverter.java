package com.cooba.enums.converter;

import com.cooba.enums.GoodsStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GoodsStatusConverter implements AttributeConverter<GoodsStatusEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(GoodsStatusEnum attribute) {
        return attribute.getType();
    }

    @Override
    public GoodsStatusEnum convertToEntityAttribute(Integer dbData) {
        return GoodsStatusEnum.getEnum(dbData).orElse(null);
    }
}
