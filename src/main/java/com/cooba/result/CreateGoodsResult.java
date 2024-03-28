package com.cooba.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateGoodsResult {
    private Long goodsId;
    private String name;
    private Integer assetId;
    private String assetName;
    private BigDecimal price;
}
