package com.cooba.component.shop;


import com.cooba.request.*;
import com.cooba.result.CreateGoodsResult;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.PayResult;

public interface Shop {

    CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest);

    CreateGoodsResult createGoods(CreateGoodsRequest createGoodsRequest);

    void restockGoods(RestockRequest restockRequest);

    void sendGoods(PayResult payResult, BuyRequest buyRequest);
}
