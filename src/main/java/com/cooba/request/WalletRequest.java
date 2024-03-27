package com.cooba.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WalletRequest {
    @NotNull
    private Long userId;
    private String orderId;
    @NotNull
    private Integer assetId;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
}
