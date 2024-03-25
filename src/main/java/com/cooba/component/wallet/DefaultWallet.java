package com.cooba.component.wallet;

import com.cooba.entity.WalletEntity;
import com.cooba.enums.WalletEnum;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.repository.WalletRepository;
import com.cooba.result.WalletChangeResult;
import com.cooba.util.lock.CustomLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultWallet implements Wallet {
    private final WalletRepository walletRepository;
    private final CustomLock customLock;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletChangeResult increaseAsset(long userId, int assetId, BigDecimal amount) {
        String key = this.getEnum().name() + userId + assetId;

        return customLock.tryLock(key, 1, TimeUnit.SECONDS, () -> {
            WalletEntity walletEntity = walletRepository.findByUserIdAndAssetId(userId, assetId)
                    .orElseGet(() -> createNewWallet(userId, assetId));
            BigDecimal transferBalance = walletEntity.getBalance().add(amount);
            walletEntity.setBalance(transferBalance);
            walletEntity.setUpdateTime(LocalDateTime.now());
            walletRepository.save(walletEntity);
            return WalletChangeResult.builder()
                    .transferBalance(transferBalance)
                    .build();
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletChangeResult decreaseAsset(long userId, int assetId, BigDecimal amount) {
        String key = this.getEnum().name() + userId + assetId;

        return customLock.tryLock(key, 1, TimeUnit.SECONDS, () -> {
            WalletEntity walletEntity = walletRepository.findByUserIdAndAssetId(userId, assetId)
                    .orElseGet(() -> createNewWallet(userId, assetId));
            BigDecimal transferBalance = walletEntity.getBalance().subtract(amount);

            if (transferBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException();
            }

            walletEntity.setBalance(transferBalance);
            walletEntity.setUpdateTime(LocalDateTime.now());
            walletRepository.save(walletEntity);
            return WalletChangeResult.builder()
                    .transferBalance(transferBalance)
                    .build();
        });
    }

    private WalletEntity createNewWallet(long userId, int assetId) {
        LocalDateTime now = LocalDateTime.now();
        WalletEntity walletEntity = WalletEntity.builder()
                .userId(userId)
                .assetId(assetId)
                .balance(BigDecimal.ZERO)
                .createdTime(now)
                .updateTime(now)
                .build();
        return walletRepository.save(walletEntity);
    }

    @Override
    public WalletEnum getEnum() {
        return WalletEnum.DEFAULT;
    }
}
