package com.cooba.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RestockRequest {
    private Long userId;
    private Integer merchantId;
    private String orderId;
    private List<GoodsAmountRequest> goodsAmountRequests;
}
