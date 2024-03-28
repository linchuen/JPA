package com.cooba.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class GoodsResponse {
    private Long id;
    private Integer merchantId;
    private String name;
    private BigDecimal remainAmount;
    private List<Price> priceList;

    @Data
    @Builder
    public static class Price {
        private Integer assetId;
        private BigDecimal price;
    }
}
