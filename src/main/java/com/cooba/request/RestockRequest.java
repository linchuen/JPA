package com.cooba.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RestockRequest {
    @NotNull
    private Long adminUserId;
    @NotNull
    private Integer merchantId;
    private String orderId;
    @NotNull
    private List<GoodsAmountRequest> goodsAmountRequests;
}
