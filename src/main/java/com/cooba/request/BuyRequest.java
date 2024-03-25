package com.cooba.request;

import lombok.Data;

import java.util.List;

@Data
public class BuyRequest {
    private Long userId;
    private List<GoodsAmountRequest> goodsAmountRequests;
    private Integer paymentAssetId;
}
