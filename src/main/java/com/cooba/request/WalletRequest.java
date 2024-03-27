package com.cooba.request;

import com.cooba.annotation.AmountValidation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WalletRequest {
    @NotNull
    private Long userId;
    private String orderId;
    @NotNull
    private Integer assetId;
    @AmountValidation
    private BigDecimal amount;
}
