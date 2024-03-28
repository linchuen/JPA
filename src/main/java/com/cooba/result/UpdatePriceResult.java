package com.cooba.result;


import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(fluent = true)
public class UpdatePriceResult {
    private Long goodsId;
    private Integer assetId;
    private BigDecimal price;
}
