package com.cooba.service.goods;

import com.cooba.component.shop.Shop;
import com.cooba.request.CreateGoodsRequest;
import com.cooba.result.CreateGoodsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final Shop shop;

    public CreateGoodsResult createGoods(CreateGoodsRequest createGoodsRequest) {
        return shop.createGoods(createGoodsRequest);
    }

}
