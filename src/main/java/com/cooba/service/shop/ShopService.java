package com.cooba.service.shop;

import com.cooba.request.BuyRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.request.RestockRequest;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.PayResult;
import com.cooba.result.RestockResult;

public interface ShopService {
    CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest);

    RestockResult restockGoods(RestockRequest restockRequest);

    PayResult saleGoods(BuyRequest buyRequest);
}
