package com.cooba.service.goods;

import com.cooba.component.shop.Shop;
import com.cooba.entity.GoodsEntity;
import com.cooba.exception.MerchantNotExistException;
import com.cooba.exception.UserNotExistException;
import com.cooba.repository.GoodsRepository;
import com.cooba.repository.MerchantRepository;
import com.cooba.request.CreateGoodsRequest;
import com.cooba.result.CreateGoodsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final Shop shop;
    private final MerchantRepository merchantRepository;
    private final GoodsRepository goodsRepository;

    public CreateGoodsResult createGoods(CreateGoodsRequest createGoodsRequest) {
        Integer merchantId = createGoodsRequest.getMerchantId();
        merchantRepository.findById(merchantId).orElseThrow(MerchantNotExistException::new);

        return shop.createGoods(createGoodsRequest);
    }

    @Override
    public List<GoodsEntity> listGoods() {
        return goodsRepository.findAll();
    }

}
