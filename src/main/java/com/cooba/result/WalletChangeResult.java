package com.cooba.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletChangeResult {
    private BigDecimal transferBalance;
}
