package com.cooba.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PayResult {
    private boolean isSuccess;
    private BigDecimal transferBalance;
    private BigDecimal totalPrice;
}
