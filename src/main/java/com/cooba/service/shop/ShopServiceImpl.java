package com.cooba.service.shop;

import com.cooba.component.goodsorder.GoodsOrder;
import com.cooba.component.shop.Shop;
import com.cooba.component.user.User;
import com.cooba.component.user.UserFactory;
import com.cooba.entity.GoodsOrderEntity;
import com.cooba.enums.GoodsTransferEnum;
import com.cooba.enums.UserEnum;
import com.cooba.exception.UserNotExistException;
import com.cooba.repository.UserRepository;
import com.cooba.request.BuyRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.request.GoodsAmountRequest;
import com.cooba.result.BuyResult;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.PayResult;
import com.cooba.result.SendGoodsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final Shop shop;
    private final UserFactory userFactory;
    private final GoodsOrder goodsOrder;
    private final UserRepository userRepository;

    @Override
    public CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest) {
        return shop.createMerchant(createMerchantRequest);
    }

    @Override
    public PayResult saleGoods(BuyRequest buyRequest) {
        Long userId = buyRequest.getUserId();
        String orderId = buyRequest.getOrderId();

        userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        GoodsOrderEntity order = goodsOrder.create(orderId, userId);

        User user = userFactory.getByEnum(UserEnum.DEFAULT);
        PayResult payResult = user.buy(buyRequest);
        shop.sendGoods(payResult, buyRequest);
        goodsOrder.updateStatus(order);
        return payResult;
    }
}
