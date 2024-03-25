package com.cooba.component.wallet_order;

import com.cooba.entity.WalletOrderEntity;
import com.cooba.enums.TransferTypeEnum;
import com.cooba.enums.WalletStatusEnum;
import com.cooba.repository.WalletRecordRepository;
import com.cooba.request.WalletRequest;
import com.cooba.result.WalletChangeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class WalletOrderImpl implements WalletOrder {
    private final WalletRecordRepository walletRecordRepository;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public WalletOrderEntity create(WalletRequest walletRequest, TransferTypeEnum transferType) {
        WalletOrderEntity order = WalletOrderEntity.builder()
                .orderId(walletRequest.getOrderId())
                .userId(walletRequest.getUserId())
                .assetId(walletRequest.getAssetId())
                .amount(walletRequest.getAmount())
                .transferType(transferType.getType())
                .status(WalletStatusEnum.FAILED.getType())
                .createdTime(LocalDateTime.now())
                .build();
        walletRecordRepository.save(order);
        return order;
    }

    @Override
    public void updateStatus(WalletOrderEntity order, WalletChangeResult walletChangeResult) {
        order.setTransferBalance(walletChangeResult.getTransferBalance());
        order.setStatus(WalletStatusEnum.SUCCEED.getType());
        walletRecordRepository.save(order);
    }
}
