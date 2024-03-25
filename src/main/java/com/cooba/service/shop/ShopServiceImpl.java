package com.cooba.service.shop;

import com.cooba.component.shop.Shop;
import com.cooba.component.user.User;
import com.cooba.component.user.UserFactory;
import com.cooba.enums.UserEnum;
import com.cooba.exception.UserNotExistException;
import com.cooba.repository.UserRepository;
import com.cooba.request.BuyRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.result.BuyResult;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.PayResult;
import com.cooba.result.SendGoodsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService{
    private final Shop shop;
    private final UserFactory userFactory;
    private final UserRepository userRepository;
    @Override
    public CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest) {
       return shop.createMerchant(createMerchantRequest);
    }

    @Override
    public BuyResult saleGoods(BuyRequest buyRequest) {
        userRepository.findById(buyRequest.getUserId()).orElseThrow(UserNotExistException::new);

        User user = userFactory.getByEnum(UserEnum.DEFAULT);
        PayResult payResult = user.buy(buyRequest);
        SendGoodsResult sendGoodsResult = shop.sendGoods(payResult);
        return new BuyResult();
    }
}
