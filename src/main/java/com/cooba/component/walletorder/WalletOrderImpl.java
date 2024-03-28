package com.cooba.component.walletorder;

import com.cooba.entity.WalletOrderEntity;
import com.cooba.enums.WalletStatusEnum;
import com.cooba.enums.WalletTransferEnum;
import com.cooba.repository.WalletRecordRepository;
import com.cooba.request.WalletRequest;
import com.cooba.result.WalletChangeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class WalletOrderImpl implements WalletOrder {
    private final WalletRecordRepository walletRecordRepository;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public WalletOrderEntity create(WalletRequest walletRequest, WalletTransferEnum transferType) {
        boolean isWithdraw = transferType == WalletTransferEnum.WITHDRAW;
        BigDecimal amount = walletRequest.getAmount();

        WalletOrderEntity order = WalletOrderEntity.builder()
                .orderId(walletRequest.getOrderId())
                .userId(walletRequest.getUserId())
                .assetId(walletRequest.getAssetId())
                .amount(isWithdraw ? amount.negate() : amount)
                .transferType(transferType)
                .status(WalletStatusEnum.FAILED)
                .build();
        walletRecordRepository.save(order);
        return order;
    }

    @Override
    public void updateStatus(WalletOrderEntity order, WalletChangeResult walletChangeResult) {
        order.setTransferBalance(walletChangeResult.getTransferBalance());
        order.setStatus(WalletStatusEnum.SUCCEED);
        walletRecordRepository.save(order);
    }
}
