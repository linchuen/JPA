package com.cooba.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BuyRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Integer merchantId;
    private String orderId;
    @NotNull
    private List<GoodsAmountRequest> goodsAmountRequests;
    @NotNull
    private Integer paymentAssetId;
}
