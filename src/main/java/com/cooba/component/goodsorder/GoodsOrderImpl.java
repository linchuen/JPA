package com.cooba.component.goodsorder;

import com.cooba.entity.GoodsOrderEntity;
import com.cooba.enums.GoodsStatusEnum;
import com.cooba.enums.UserTypeEnum;
import com.cooba.repository.GoodsOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class GoodsOrderImpl implements GoodsOrder {
    private final GoodsOrderRepository goodsOrderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public GoodsOrderEntity create(String orderId, Long userId, UserTypeEnum userType) {
        LocalDateTime now = LocalDateTime.now();
        GoodsOrderEntity order = GoodsOrderEntity.builder()
                .orderId(orderId)
                .userId(userId)
                .userType(userType.getType())
                .status(GoodsStatusEnum.FAILED.getType())
                .createdTime(now)
                .updatedTime(now)
                .build();
        return goodsOrderRepository.save(order);
    }

    @Override
    public void updateStatus(GoodsOrderEntity order) {
        order.setStatus(GoodsStatusEnum.SUCCEED.getType());
        order.setUpdatedTime(LocalDateTime.now());
        goodsOrderRepository.save(order);
    }
}
