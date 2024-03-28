package com.cooba.component.goodsrecord;

import com.cooba.enums.GoodsTransferEnum;
import com.cooba.result.InventoryChangeResult;

import java.math.BigDecimal;

public interface GoodsRecord {
    void insertRecord(String orderId, Integer merchantId, Long goodsId, BigDecimal amount, GoodsTransferEnum transferType, InventoryChangeResult inventoryChangeResult);
}
