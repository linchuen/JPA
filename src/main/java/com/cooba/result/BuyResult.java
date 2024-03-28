package com.cooba.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class BuyResult {
    private boolean isSuccess;
    private BigDecimal transferBalance;
    private BigDecimal totalPrice;

    private List<SendGoodsResult.SendGoodInfo> sendGoodInfoList;
}
