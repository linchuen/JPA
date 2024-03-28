package com.cooba.component.shop;


import com.cooba.request.*;
import com.cooba.result.CreateGoodsResult;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.PayResult;
import com.cooba.result.RestockResult;
import com.cooba.result.UpdatePriceResult;

import java.math.BigDecimal;

public interface Shop {

    CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest);

    CreateGoodsResult createGoods(CreateGoodsRequest createGoodsRequest);

    RestockResult restockGoods(RestockRequest restockRequest);

    UpdatePriceResult updateGoodsPrice(Long goodsId, Integer assetId, BigDecimal price);

    void sendGoods(PayResult payResult, BuyRequest buyRequest);
}
