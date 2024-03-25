package com.cooba.component.shop;


import com.cooba.request.CreateGoodsRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.request.RestockRequest;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.PayResult;
import com.cooba.result.SendGoodsResult;

public interface Shop  {

    CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest);

    void createGoods(CreateGoodsRequest createGoodsRequest);

    void restockGoods(RestockRequest restockRequest);

    SendGoodsResult sendGoods(PayResult payResult);
}
