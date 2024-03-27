package com.cooba.request;

import lombok.Data;

import java.util.List;

@Data
public class RestockRequest {
    private Long adminUserId;
    private Integer merchantId;
    private String orderId;
    private List<GoodsAmountRequest> goodsAmountRequests;
}
