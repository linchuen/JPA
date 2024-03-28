package com.cooba.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(fluent = true)
public class InventoryChangeResult {
    private Long goodsId;
    private BigDecimal remainAmount;
}
