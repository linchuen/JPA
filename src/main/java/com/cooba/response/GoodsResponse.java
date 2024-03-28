package com.cooba.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(fluent = true)
public class GoodsResponse {
    private Long id;
    private Integer merchantId;
    private String name;
    private BigDecimal remainAmount;
    private List<Price> priceList;

    @Data
    @Accessors(fluent = true)
    public static class Price {
        private Integer assetId;
        private BigDecimal price;
    }
}
