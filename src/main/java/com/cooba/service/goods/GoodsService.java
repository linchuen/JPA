package com.cooba.service.goods;

import com.cooba.request.CreateGoodsRequest;
import com.cooba.response.GoodsResponse;
import com.cooba.result.CreateGoodsResult;

import java.util.List;

public interface GoodsService {

    CreateGoodsResult createGoods(CreateGoodsRequest createGoodsRequest);

    List<GoodsResponse> listGoods();
}
