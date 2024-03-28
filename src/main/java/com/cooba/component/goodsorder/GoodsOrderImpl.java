package com.cooba.component.goodsorder;

import com.cooba.entity.GoodsOrderEntity;
import com.cooba.enums.GoodsStatusEnum;
import com.cooba.enums.UserTypeEnum;
import com.cooba.repository.GoodsOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GoodsOrderImpl implements GoodsOrder {
    private final GoodsOrderRepository goodsOrderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public GoodsOrderEntity create(String orderId, Long userId, UserTypeEnum userType) {
        GoodsOrderEntity order = new GoodsOrderEntity()
                .orderId(orderId)
                .userId(userId)
                .userType(userType.getType())
                .status(GoodsStatusEnum.FAILED.getType());
        return goodsOrderRepository.save(order);
    }

    @Override
    public void updateStatus(GoodsOrderEntity order) {
        order.status(GoodsStatusEnum.SUCCEED.getType());
        goodsOrderRepository.save(order);
    }
}
