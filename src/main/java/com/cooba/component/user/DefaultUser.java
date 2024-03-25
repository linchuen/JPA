package com.cooba.component.user;

import com.cooba.component.wallet_order.WalletOrder;
import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.entity.UserEntity;
import com.cooba.entity.WalletOrderEntity;
import com.cooba.enums.*;
import com.cooba.repository.GoodsInventoryRepository;
import com.cooba.repository.UserRepository;
import com.cooba.request.BuyRequest;
import com.cooba.request.CreateUserRequest;
import com.cooba.request.WalletRequest;
import com.cooba.result.PayResult;
import com.cooba.result.CreateUserResult;
import com.cooba.result.WalletChangeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DefaultUser implements User {
    private final WalletFactory walletFactory;
    private final UserRepository userRepository;
    private final WalletOrder walletOrder;
    private final GoodsInventoryRepository goodsInventoryRepository;

    @Override
    public CreateUserResult create(CreateUserRequest createUserRequest) {
        LocalDateTime now = LocalDateTime.now();
        UserEntity userEntity = UserEntity.builder()
                .name(createUserRequest.getName())
                .createdTime(now)
                .updateTime(now)
                .build();
        userRepository.save(userEntity);
        return CreateUserResult.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletChangeResult deposit(WalletRequest walletRequest) {
        Long userId = walletRequest.getUserId();
        Integer assetId = walletRequest.getAssetId();
        BigDecimal amount = walletRequest.getAmount();

        WalletOrderEntity order = walletOrder.create(walletRequest, TransferTypeEnum.DEPOSIT);

        Wallet wallet = walletFactory.getByEnum(WalletEnum.DEFAULT);
        WalletChangeResult walletChangeResult = wallet.increaseAsset(userId, assetId, amount);
        walletOrder.updateStatus(order, walletChangeResult);
        return walletChangeResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletChangeResult withdraw(WalletRequest walletRequest) {
        Long userId = walletRequest.getUserId();
        Integer assetId = walletRequest.getAssetId();
        BigDecimal amount = walletRequest.getAmount();

        WalletOrderEntity order = walletOrder.create(walletRequest, TransferTypeEnum.WITHDRAW);

        Wallet wallet = walletFactory.getByEnum(WalletEnum.DEFAULT);
        WalletChangeResult walletChangeResult = wallet.decreaseAsset(userId, assetId, amount);
        walletOrder.updateStatus(order, walletChangeResult);
        return walletChangeResult;
    }

    @Override
    public PayResult buy(BuyRequest buyRequest) {

        return null;
    }


    @Override
    public UserEnum getEnum() {
        return UserEnum.DEFAULT;
    }
}
