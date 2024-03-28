package com.cooba.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(fluent = true)
public class WalletChangeResult {
    private BigDecimal transferBalance;
}
