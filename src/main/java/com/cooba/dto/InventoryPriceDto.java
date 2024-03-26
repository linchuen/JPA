package com.cooba.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventoryPriceDto {
    private Integer merchantId;
    private Long goodsId;
    private BigDecimal remainAmount;
    private Integer assetId;
    private BigDecimal price;
}
