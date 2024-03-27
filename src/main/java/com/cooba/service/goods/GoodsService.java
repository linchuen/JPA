package com.cooba.service.goods;

import com.cooba.request.CreateGoodsRequest;
import com.cooba.result.CreateGoodsResult;

public interface GoodsService {

    CreateGoodsResult createGoods(CreateGoodsRequest createGoodsRequest);
}
