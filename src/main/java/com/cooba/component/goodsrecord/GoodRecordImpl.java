package com.cooba.component.goodsrecord;

import com.cooba.entity.GoodsRecordEntity;
import com.cooba.enums.GoodsTransferEnum;
import com.cooba.repository.GoodsRecordRepository;
import com.cooba.result.InventoryChangeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class GoodRecordImpl implements GoodsRecord {
    private final GoodsRecordRepository goodsRecordRepository;

    @Override
    public void insertRecord(String orderId, Integer merchantId, Long goodsId, BigDecimal amount, GoodsTransferEnum transferType, InventoryChangeResult inventoryChangeResult) {
        boolean isSale = transferType == GoodsTransferEnum.SALE;

        GoodsRecordEntity record = GoodsRecordEntity.builder()
                .orderId(orderId)
                .merchantId(merchantId)
                .goodsId(goodsId)
                .transferType(transferType.getType())
                .changeAmount(isSale ? amount.negate() : amount)
                .remainAmount(inventoryChangeResult.getRemainAmount())
                .build();
        goodsRecordRepository.save(record);
    }
}
