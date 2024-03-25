package com.cooba.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RestockRequest {
    private List<GoodsAmountRequest> goodsAmountRequests;
}
