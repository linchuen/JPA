package com.cooba.service.goods;

import com.cooba.request.CreateGoodsRequest;
import com.cooba.request.UpdatePriceRequest;
import com.cooba.response.GoodsResponse;
import com.cooba.result.CreateGoodsResult;
import com.cooba.result.UpdatePriceResult;

import java.util.List;

public interface GoodsService {

    CreateGoodsResult createGoods(CreateGoodsRequest createGoodsRequest);

    UpdatePriceResult updatePrice(UpdatePriceRequest updatePriceRequest);

    List<GoodsResponse> listGoods();
}
