package com.cooba.service.goods;

import com.cooba.component.shop.Shop;
import com.cooba.entity.GoodsEntity;
import com.cooba.exception.MerchantNotExistException;
import com.cooba.repository.GoodsRepository;
import com.cooba.repository.MerchantRepository;
import com.cooba.request.CreateGoodsRequest;
import com.cooba.response.GoodsResponse;
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
    public List<GoodsResponse> listGoods() {
        return goodsRepository.findAll().stream()
                .map(goodsEntity -> GoodsResponse.builder()
                        .merchantId(goodsEntity.getMerchantId())
                        .name(goodsEntity.getName())
                        .remainAmount(goodsEntity.getInventory().getRemainAmount())
                        .priceList(getPriceList(goodsEntity))
                        .build())
                .toList();
    }

    private List<GoodsResponse.Price> getPriceList(GoodsEntity goodsEntity) {
        return goodsEntity.getPrice().stream()
                .map(goodsPrice -> GoodsResponse.Price.builder()
                        .price(goodsPrice.getPrice())
                        .assetId(goodsPrice.getAssetId())
                        .build())
                .toList();
    }

}
