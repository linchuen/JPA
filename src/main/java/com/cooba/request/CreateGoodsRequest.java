package com.cooba.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateGoodsRequest {
    @NotNull
    private Integer merchantId;
    @NotBlank
    private String name;

    private Integer assetId;
    private BigDecimal price;
}
