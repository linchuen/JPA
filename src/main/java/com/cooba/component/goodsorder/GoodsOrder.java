package com.cooba.component.goodsorder;

import com.cooba.entity.GoodsOrderEntity;
import com.cooba.enums.UserTypeEnum;

public interface GoodsOrder {
    GoodsOrderEntity create(String orderId, Long userId, UserTypeEnum userType);

    void updateStatus(GoodsOrderEntity order);
}
