package com.cooba.service.shop;

import com.cooba.component.goodsorder.GoodsOrder;
import com.cooba.component.shop.Shop;
import com.cooba.component.user.User;
import com.cooba.component.user.UserFactory;
import com.cooba.entity.GoodsEntity;
import com.cooba.entity.GoodsOrderEntity;
import com.cooba.enums.UserEnum;
import com.cooba.enums.UserTypeEnum;
import com.cooba.exception.GoodsNotExistException;
import com.cooba.exception.MerchantNotExistException;
import com.cooba.exception.UserNotExistException;
import com.cooba.repository.AdminRepository;
import com.cooba.repository.GoodsRepository;
import com.cooba.repository.MerchantRepository;
import com.cooba.repository.UserRepository;
import com.cooba.request.BuyRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.request.GoodsAmountRequest;
import com.cooba.request.RestockRequest;
import com.cooba.result.BuyResult;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.PayResult;
import com.cooba.result.RestockResult;
import com.cooba.result.SendGoodsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final Shop shop;
    private final UserFactory userFactory;
    private final GoodsOrder goodsOrder;
    //Repository
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final MerchantRepository merchantRepository;
    private final GoodsRepository goodsRepository;

    @Override
    public CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest) {
        return shop.createMerchant(createMerchantRequest);
    }

    @Override
    public RestockResult restockGoods(RestockRequest restockRequest) {
        Long adminUserId = restockRequest.getAdminUserId();
        Integer merchantId = restockRequest.getMerchantId();
        String orderId = restockRequest.getOrderId();
        List<Long> goodsIds = restockRequest.getGoodsAmountRequests().stream()
                .map(GoodsAmountRequest::getGoodsId)
                .toList();

        adminRepository.findById(adminUserId).orElseThrow(UserNotExistException::new);
        merchantRepository.findById(merchantId).orElseThrow(MerchantNotExistException::new);
        List<GoodsEntity> goodsEntities = goodsRepository.findAllById(goodsIds);
        if (goodsIds.size() != goodsEntities.size()) {
            throw new GoodsNotExistException();
        }

        GoodsOrderEntity order = goodsOrder.create(orderId, adminUserId, UserTypeEnum.ADMIN);

        RestockResult restockResult = shop.restockGoods(restockRequest);
        goodsOrder.updateStatus(order);
        return restockResult;
    }

    @Override
    public BuyResult saleGoods(BuyRequest buyRequest) {
        Long userId = buyRequest.getUserId();
        Integer merchantId = buyRequest.getMerchantId();
        String orderId = buyRequest.getOrderId();
        List<Long> goodsIds = buyRequest.getGoodsAmountRequests().stream()
                .map(GoodsAmountRequest::getGoodsId)
                .toList();

        userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        merchantRepository.findById(merchantId).orElseThrow(MerchantNotExistException::new);
        List<GoodsEntity> goodsEntities = goodsRepository.findAllById(goodsIds);
        if (goodsIds.size() != goodsEntities.size()) {
            throw new GoodsNotExistException();
        }

        GoodsOrderEntity order = goodsOrder.create(orderId, userId, UserTypeEnum.USER);

        User user = userFactory.getByEnum(UserEnum.DEFAULT);
        PayResult payResult = user.pay(buyRequest);
        SendGoodsResult sendGoodsResult = shop.sendGoods(payResult, buyRequest);
        goodsOrder.updateStatus(order);
        return BuyResult.builder()
                .isSuccess(true)
                .totalPrice(payResult.getTotalPrice())
                .transferBalance(payResult.getTransferBalance())
                .sendGoodInfoList(sendGoodsResult.getSendGoodInfoList())
                .build();
    }
}
