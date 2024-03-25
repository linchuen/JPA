package com.cooba.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsAmountRequest {
    private Long goodsId;
    private BigDecimal amount;
}
