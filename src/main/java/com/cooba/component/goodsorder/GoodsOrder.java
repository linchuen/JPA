package com.cooba.component.goodsorder;

import com.cooba.entity.GoodsOrderEntity;

public interface GoodsOrder {
    GoodsOrderEntity create(String orderId, Long userId);

    void updateStatus(GoodsOrderEntity order);
}
