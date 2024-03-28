package com.cooba.service.goods;

import com.cooba.component.shop.Shop;
import com.cooba.entity.GoodsEntity;
import com.cooba.exception.MerchantNotExistException;
import com.cooba.repository.GoodsRepository;
import com.cooba.repository.MerchantRepository;
import com.cooba.request.CreateGoodsRequest;
import com.cooba.request.UpdatePriceRequest;
import com.cooba.response.GoodsResponse;
import com.cooba.result.CreateGoodsResult;
import com.cooba.result.UpdatePriceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public UpdatePriceResult updatePrice(UpdatePriceRequest updatePriceRequest) {
        Long goodsId = updatePriceRequest.getGoodsId();
        Integer assetId = updatePriceRequest.getAssetId();
        BigDecimal price = updatePriceRequest.getPrice();
        return shop.updateGoodsPrice(goodsId, assetId, price);
    }

    @Override
    public List<GoodsResponse> listGoods() {
        return goodsRepository.findAll().stream()
                .map(goodsEntity ->new GoodsResponse()
                        .merchantId(goodsEntity.merchantId())
                        .name(goodsEntity.name())
                        .remainAmount(goodsEntity.inventory().remainAmount())
                        .priceList(getPriceList(goodsEntity)))
                .toList();
    }

    private List<GoodsResponse.Price> getPriceList(GoodsEntity goodsEntity) {
        return goodsEntity.price().stream()
                .map(goodsPrice -> new GoodsResponse.Price()
                        .price(goodsPrice.price())
                        .assetId(goodsPrice.assetId()))
                .toList();
    }

}
