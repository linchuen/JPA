package com.cooba.component.user;

import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.component.walletorder.WalletOrder;
import com.cooba.entity.GoodsEntity;
import com.cooba.entity.GoodsPriceEntity;
import com.cooba.entity.UserEntity;
import com.cooba.entity.WalletEntity;
import com.cooba.entity.WalletOrderEntity;
import com.cooba.enums.AssetEnum;
import com.cooba.enums.OrderEnum;
import com.cooba.enums.UserEnum;
import com.cooba.enums.WalletEnum;
import com.cooba.enums.WalletTransferEnum;
import com.cooba.exception.CurrencyNotSupportException;
import com.cooba.exception.EmptyStockException;
import com.cooba.repository.GoodsRepository;
import com.cooba.repository.UserRepository;
import com.cooba.request.BuyRequest;
import com.cooba.request.CreateUserRequest;
import com.cooba.request.GoodsAmountRequest;
import com.cooba.request.WalletRequest;
import com.cooba.result.CreateUserResult;
import com.cooba.result.PayResult;
import com.cooba.result.WalletChangeResult;
import com.cooba.util.order.OrderNumGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DefaultUser implements User {
    private final WalletFactory walletFactory;
    private final UserRepository userRepository;
    private final WalletOrder walletOrder;
    private final GoodsRepository goodsRepository;
    private final OrderNumGenerator orderNumGenerator;

    @Override
    public CreateUserResult create(CreateUserRequest createUserRequest) {
        UserEntity userEntity = new UserEntity()
                .name(createUserRequest.getName());
        WalletEntity walletEntity = new WalletEntity()
                .assetId(AssetEnum.TWD.getId())
                .balance(BigDecimal.ZERO)
                .user(userEntity);
        userEntity.wallet(List.of(walletEntity));

        userRepository.save(userEntity);
        return new CreateUserResult()
                .id(userEntity.id())
                .name(userEntity.name());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletChangeResult deposit(WalletRequest walletRequest) {
        Long userId = walletRequest.getUserId();
        Integer assetId = walletRequest.getAssetId();
        BigDecimal amount = walletRequest.getAmount();

        WalletOrderEntity order = walletOrder.create(walletRequest, WalletTransferEnum.DEPOSIT);

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

        WalletOrderEntity order = walletOrder.create(walletRequest, WalletTransferEnum.WITHDRAW);

        Wallet wallet = walletFactory.getByEnum(WalletEnum.DEFAULT);
        WalletChangeResult walletChangeResult = wallet.decreaseAsset(userId, assetId, amount);
        walletOrder.updateStatus(order, walletChangeResult);
        return walletChangeResult;
    }

    @Override
    public PayResult pay(BuyRequest buyRequest) {
        Long userId = buyRequest.getUserId();
        Integer paymentAssetId = buyRequest.getPaymentAssetId();

        Map<Long, BigDecimal> idAmountMap = buyRequest.getGoodsAmountRequests().stream()
                .collect(Collectors.toMap(
                        GoodsAmountRequest::getGoodsId,
                        GoodsAmountRequest::getAmount));
        List<GoodsEntity> goodsEntities = goodsRepository.findAllById(idAmountMap.keySet());

        boolean isEmptyStock = goodsEntities.stream().map(GoodsEntity::inventory)
                .anyMatch(inventory -> inventory.remainAmount().compareTo(BigDecimal.ZERO) == 0);
        if (isEmptyStock) {
            throw new EmptyStockException();
        }

        List<GoodsPriceEntity> goodsPriceEntities = goodsEntities.stream()
                .flatMap(goodsEntity -> goodsEntity.price().stream())
                .filter(goodsPriceEntity -> goodsPriceEntity.assetId().equals(paymentAssetId))
                .toList();
        if (goodsPriceEntities.size() != idAmountMap.size()) {
            throw new CurrencyNotSupportException();
        }

        BigDecimal totalPrice = goodsPriceEntities.stream()
                .map(price -> price.price().multiply(idAmountMap.get(price.goods().id())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String orderId = orderNumGenerator.generate(OrderEnum.WALLET);

        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setUserId(userId);
        walletRequest.setOrderId(orderId);
        walletRequest.setAssetId(paymentAssetId);
        walletRequest.setAmount(totalPrice);
        WalletOrderEntity order = walletOrder.create(walletRequest, WalletTransferEnum.WITHDRAW);

        Wallet wallet = walletFactory.getByEnum(WalletEnum.DEFAULT);
        WalletChangeResult walletChangeResult = wallet.decreaseAsset(userId, paymentAssetId, totalPrice);
        walletOrder.updateStatus(order, walletChangeResult);

        return new PayResult()
                .isSuccess(true)
                .transferBalance(walletChangeResult.transferBalance())
                .totalPrice(totalPrice);
    }


    @Override
    public UserEnum getEnum() {
        return UserEnum.DEFAULT;
    }
}
