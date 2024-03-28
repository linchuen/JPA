package com.cooba.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(fluent = true)
public class PayResult {
    private boolean isSuccess;
    private BigDecimal transferBalance;
    private BigDecimal totalPrice;
}
