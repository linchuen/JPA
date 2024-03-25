package com.cooba.service.shop;

import com.cooba.request.BuyRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.result.BuyResult;
import com.cooba.result.CreateMerchantResult;

public interface ShopService {
    CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest);

    BuyResult saleGoods(BuyRequest buyRequest);
}
