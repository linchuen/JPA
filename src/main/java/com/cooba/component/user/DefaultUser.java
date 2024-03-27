package com.cooba.component.user;

import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.component.walletorder.WalletOrder;
import com.cooba.dto.InventoryPriceDto;
import com.cooba.entity.UserEntity;
import com.cooba.entity.WalletOrderEntity;
import com.cooba.enums.OrderEnum;
import com.cooba.enums.WalletTransferEnum;
import com.cooba.enums.UserEnum;
import com.cooba.enums.WalletEnum;
import com.cooba.exception.CurrencyNotSupportException;
import com.cooba.exception.EmptyStockException;
import com.cooba.mapper.GoodsInventoryMapper;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DefaultUser implements User {
    private final WalletFactory walletFactory;
    private final UserRepository userRepository;
    private final WalletOrder walletOrder;
    private final GoodsInventoryMapper goodsInventoryMapper;
    private final OrderNumGenerator orderNumGenerator;

    @Override
    public CreateUserResult create(CreateUserRequest createUserRequest) {
        UserEntity userEntity = UserEntity.builder()
                .name(createUserRequest.getName())
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
        List<GoodsAmountRequest> goodsAmountRequests = buyRequest.getGoodsAmountRequests();
        List<Long> idList = goodsAmountRequests.stream().map(GoodsAmountRequest::getGoodsId).collect(Collectors.toList());
        List<InventoryPriceDto> inventoryPriceDtoList = goodsInventoryMapper.findWithPriceByIds(idList);

        boolean isEmptyStock = inventoryPriceDtoList.stream()
                .anyMatch(inventoryPriceDto -> inventoryPriceDto.getRemainAmount().compareTo(BigDecimal.ZERO) == 0);
        if (isEmptyStock){
            throw new EmptyStockException();
        }

        Map<Long,BigDecimal> goodsIdPriceMap= inventoryPriceDtoList.stream()
                .filter(inventoryPriceDto -> inventoryPriceDto.getAssetId().equals(paymentAssetId))
                .collect(Collectors.toMap(InventoryPriceDto::getGoodsId, InventoryPriceDto::getPrice));
        if (goodsIdPriceMap.size()!= inventoryPriceDtoList.size()){
            throw new CurrencyNotSupportException();
        }

        BigDecimal totalPrice = goodsAmountRequests.stream()
                .map(request -> request.getAmount().multiply(goodsIdPriceMap.get(request.getGoodsId())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

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

        return PayResult.builder()
                .isSuccess(true)
                .transferBalance(walletChangeResult.getTransferBalance())
                .totalPrice(totalPrice)
                .build();
    }


    @Override
    public UserEnum getEnum() {
        return UserEnum.DEFAULT;
    }
}
