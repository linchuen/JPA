package com.cooba.request;

import lombok.Data;

import java.util.List;

@Data
public class BuyRequest {
    private Long userId;
    private Integer merchantId;
    private String orderId;
    private List<GoodsAmountRequest> goodsAmountRequests;
    private Integer paymentAssetId;
}
