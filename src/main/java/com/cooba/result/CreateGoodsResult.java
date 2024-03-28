package com.cooba.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(fluent = true)
public class CreateGoodsResult {
    private Long goodsId;
    private String name;
    private Integer assetId;
    private String assetName;
    private BigDecimal price;
}
