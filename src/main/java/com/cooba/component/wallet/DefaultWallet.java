package com.cooba.component.wallet;

import com.cooba.entity.UserEntity;
import com.cooba.entity.WalletEntity;
import com.cooba.enums.WalletEnum;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.repository.WalletRepository;
import com.cooba.result.WalletChangeResult;
import com.cooba.util.lock.CustomLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultWallet implements Wallet {
    private final CustomLock customLock;
    //Repository
    private final WalletRepository walletRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletChangeResult increaseAsset(long userId, int assetId, BigDecimal amount) {
        String key = this.getEnum().name() + userId + assetId;

        return customLock.tryLock(key, 3, TimeUnit.SECONDS, () -> {
            WalletEntity walletEntity = walletRepository.findByUserIdAndAssetId(userId, assetId)
                    .orElseGet(() -> createNewWallet(userId, assetId));
            BigDecimal transferBalance = walletEntity.getBalance().add(amount);
            walletEntity.setBalance(transferBalance);
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

        return customLock.tryLock(key, 3, TimeUnit.SECONDS, () -> {
            WalletEntity walletEntity = walletRepository.findByUserIdAndAssetId(userId, assetId)
                    .orElseThrow(InsufficientBalanceException::new);
            BigDecimal transferBalance = walletEntity.getBalance().subtract(amount);

            if (transferBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException();
            }

            walletEntity.setBalance(transferBalance);
            walletRepository.save(walletEntity);
            return WalletChangeResult.builder()
                    .transferBalance(transferBalance)
                    .build();
        });
    }

    private WalletEntity createNewWallet(long userId, int assetId) {
        WalletEntity walletEntity = WalletEntity.builder()
                .user(UserEntity.builder().id(userId).build())
                .assetId(assetId)
                .balance(BigDecimal.ZERO)
                .build();
        return walletRepository.save(walletEntity);
    }

    @Override
    public WalletEnum getEnum() {
        return WalletEnum.DEFAULT;
    }
}
