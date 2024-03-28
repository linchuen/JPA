package com.cooba.service.shop;

import com.cooba.request.BuyRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.request.RestockRequest;
import com.cooba.result.BuyResult;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.RestockResult;
import com.cooba.result.SendGoodsResult;

public interface ShopService {
    CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest);

    RestockResult restockGoods(RestockRequest restockRequest);

    BuyResult saleGoods(BuyRequest buyRequest);
}
