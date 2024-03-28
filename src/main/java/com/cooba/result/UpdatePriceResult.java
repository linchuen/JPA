package com.cooba.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdatePriceResult {
    private Long goodsId;
    private Integer assetId;
    private BigDecimal price;
}
