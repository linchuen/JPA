package com.cooba;

import com.cooba.controller.AdminController;
import com.cooba.controller.GoodsController;
import com.cooba.controller.ShopController;
import com.cooba.controller.UserController;
import com.cooba.enums.AssetEnum;
import com.cooba.request.*;
import com.cooba.util.MultiThreadUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@Disabled
@SpringBootTest
public class BuildTestDataTest {
    @Autowired
    AdminController adminController;
    @Autowired
    GoodsController goodsController;
    @Autowired
    ShopController shopController;
    @Autowired
    UserController userController;

    @Test
    public void buildAdmin() {
        adminController.create();
    }

    @Test
    public void buildMerchant() {
        CreateMerchantRequest createMerchantRequest = new CreateMerchantRequest();
        createMerchantRequest.setName("Merchant");
        shopController.create(createMerchantRequest);
    }

    @Test
    public void buildGoods() {
        CreateGoodsRequest goodsRequest = new CreateGoodsRequest();
        goodsRequest.setMerchantId(1);
        goodsRequest.setPrice(BigDecimal.valueOf(500));
        goodsRequest.setName("Switch");
        goodsRequest.setAssetId(AssetEnum.TWD.getId());
        goodsController.create(goodsRequest);
    }

    @Test
    public void updatePrice() {
        UpdatePriceRequest updatePriceRequest = new UpdatePriceRequest();
        updatePriceRequest.setGoodsId(1L);
        updatePriceRequest.setPrice(BigDecimal.valueOf(100));
        updatePriceRequest.setAssetId(AssetEnum.TWD.getId());
        goodsController.updatePrice(updatePriceRequest);
    }

    @Test
    public void restockGoods() {
        GoodsAmountRequest goodsAmountRequest = new GoodsAmountRequest();
        goodsAmountRequest.setGoodsId(1L);
        goodsAmountRequest.setAmount(BigDecimal.valueOf(100));

        RestockRequest restockRequest = new RestockRequest();
        restockRequest.setMerchantId(1);
        restockRequest.setAdminUserId(1L);
        restockRequest.setGoodsAmountRequests(List.of(goodsAmountRequest));
        shopController.restockGoods(restockRequest);
    }

    @Test
    public void createUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName("Aiden");
        userController.create(createUserRequest);
    }

    @Test
    public void deposit() {
        MultiThreadUtil.run(5,()->{
            WalletRequest walletRequest = new WalletRequest();
            walletRequest.setUserId(1L);
            walletRequest.setAssetId(AssetEnum.TWD.getId());
            walletRequest.setAmount(BigDecimal.TEN);
            userController.deposit(walletRequest);
        });
    }
}
