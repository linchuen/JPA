package com.cooba.component.walletorder;

import com.cooba.entity.WalletOrderEntity;
import com.cooba.enums.WalletTransferEnum;
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
    public WalletOrderEntity create(WalletRequest walletRequest, WalletTransferEnum transferType) {
        WalletOrderEntity order = new WalletOrderEntity()
                .orderId(walletRequest.getOrderId())
                .userId(walletRequest.getUserId())
                .assetId(walletRequest.getAssetId())
                .amount(walletRequest.getAmount())
                .transferType(transferType.getType())
                .status(WalletStatusEnum.FAILED.getType());
        walletRecordRepository.save(order);
        return order;
    }

    @Override
    public void updateStatus(WalletOrderEntity order, WalletChangeResult walletChangeResult) {
        order.transferBalance(walletChangeResult.transferBalance());
        order.status(WalletStatusEnum.SUCCEED.getType());
        walletRecordRepository.save(order);
    }
}
