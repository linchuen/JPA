package com.cooba.service.shop;

import com.cooba.component.goodsorder.GoodsOrder;
import com.cooba.component.shop.Shop;
import com.cooba.component.user.User;
import com.cooba.component.user.UserFactory;
import com.cooba.entity.GoodsOrderEntity;
import com.cooba.enums.UserEnum;
import com.cooba.enums.UserTypeEnum;
import com.cooba.exception.MerchantNotExistException;
import com.cooba.exception.UserNotExistException;
import com.cooba.repository.AdminRepository;
import com.cooba.repository.MerchantRepository;
import com.cooba.repository.UserRepository;
import com.cooba.request.BuyRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.request.RestockRequest;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.PayResult;
import com.cooba.result.RestockResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final Shop shop;
    private final UserFactory userFactory;
    private final GoodsOrder goodsOrder;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final MerchantRepository merchantRepository;

    @Override
    public CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest) {
        return shop.createMerchant(createMerchantRequest);
    }

    @Override
    public RestockResult restockGoods(RestockRequest restockRequest) {
        Long adminUserId = restockRequest.getAdminUserId();
        Integer merchantId = restockRequest.getMerchantId();
        String orderId = restockRequest.getOrderId();

        adminRepository.findById(adminUserId).orElseThrow(UserNotExistException::new);
        merchantRepository.findById(merchantId).orElseThrow(MerchantNotExistException::new);

        GoodsOrderEntity order = goodsOrder.create(orderId, adminUserId, UserTypeEnum.ADMIN);

        RestockResult restockResult = shop.restockGoods(restockRequest);
        goodsOrder.updateStatus(order);
        return restockResult;
    }

    @Override
    public PayResult saleGoods(BuyRequest buyRequest) {
        Long userId = buyRequest.getUserId();
        String orderId = buyRequest.getOrderId();

        userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        GoodsOrderEntity order = goodsOrder.create(orderId, userId, UserTypeEnum.USER);

        User user = userFactory.getByEnum(UserEnum.DEFAULT);
        PayResult payResult = user.pay(buyRequest);
        shop.sendGoods(payResult, buyRequest);
        goodsOrder.updateStatus(order);
        return payResult;
    }
}
