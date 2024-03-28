package com.cooba.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class UpdatePriceRequest {
    @NotNull
    private Long goodsId;
    @NotNull
    private Integer assetId;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
}
