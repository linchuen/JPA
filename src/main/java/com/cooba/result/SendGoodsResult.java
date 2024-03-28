package com.cooba.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class SendGoodsResult {
    private List<SendGoodInfo> sendGoodInfoList;

    @Data
    @Builder
    public static class SendGoodInfo{
        private Long goodsId;
        private BigDecimal changeAmount;
        private BigDecimal remainAmount;
    }
}
