package com.cooba.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class GoodsAmountRequest {
    @NotNull
    private Long goodsId;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
}
