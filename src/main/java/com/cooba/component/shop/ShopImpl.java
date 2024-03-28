package com.cooba.component.shop;


import com.cooba.component.warehouse.Warehouse;
import com.cooba.component.warehouse.WarehouseFactory;
import com.cooba.entity.GoodsEntity;
import com.cooba.entity.GoodsInventoryEntity;
import com.cooba.entity.GoodsPriceEntity;
import com.cooba.entity.GoodsRecordEntity;
import com.cooba.entity.MerchantEntity;
import com.cooba.enums.AssetEnum;
import com.cooba.enums.GoodsTransferEnum;
import com.cooba.enums.WarehouseEnum;
import com.cooba.repository.GoodsPriceRepository;
import com.cooba.repository.GoodsRecordRepository;
import com.cooba.repository.GoodsRepository;
import com.cooba.repository.MerchantRepository;
import com.cooba.request.BuyRequest;
import com.cooba.request.CreateGoodsRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.request.GoodsAmountRequest;
import com.cooba.request.RestockRequest;
import com.cooba.result.CreateGoodsResult;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.InventoryChangeResult;
import com.cooba.result.PayResult;
import com.cooba.result.RestockResult;
import com.cooba.result.UpdatePriceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShopImpl implements Shop {
    private final MerchantRepository merchantRepository;
    private final GoodsRepository goodsRepository;
    private final GoodsPriceRepository goodsPriceRepository;
    private final GoodsRecordRepository goodsRecordRepository;
    private final WarehouseFactory warehouseFactory;

    @Override
    public CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest) {
        MerchantEntity merchantEntity = MerchantEntity.builder()
                .name(createMerchantRequest.getName())
                .build();
        merchantRepository.save(merchantEntity);

        return CreateMerchantResult.builder()
                .id(merchantEntity.getId())
                .name(merchantEntity.getName())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateGoodsResult createGoods(CreateGoodsRequest createGoodsRequest) {
        Integer merchantId = createGoodsRequest.getMerchantId();
        String name = createGoodsRequest.getName();
        BigDecimal price = createGoodsRequest.getPrice();
        Integer assetId = createGoodsRequest.getAssetId();

        GoodsEntity goodsEntity = GoodsEntity.builder()
                .merchantId(merchantId)
                .name(name)
                .build();
        GoodsInventoryEntity initInventory = GoodsInventoryEntity.builder()
                .remainAmount(BigDecimal.ZERO)
                .goods(goodsEntity)
                .build();
        goodsEntity.setInventory(initInventory);

        String assetName = null;
        if (price != null && assetId != null && price.compareTo(BigDecimal.ZERO) > 0) {
            assetName = AssetEnum.getEnum(assetId).orElseThrow().name();
            GoodsPriceEntity goodsPrice = GoodsPriceEntity.builder()
                    .goods(goodsEntity)
                    .assetId(assetId)
                    .price(price)
                    .build();
            goodsEntity.setPrice(List.of(goodsPrice));
        }

        goodsRepository.save(goodsEntity);

        return CreateGoodsResult.builder()
                .goodsId(goodsEntity.getId())
                .name(name)
                .assetId(assetId)
                .assetName(assetName)
                .price(price)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestockResult restockGoods(RestockRequest restockRequest) {
        String orderId = restockRequest.getOrderId();
        Integer merchantId = restockRequest.getMerchantId();

        Warehouse warehouse = warehouseFactory.getByEnum(WarehouseEnum.DEFAULT);

        List<InventoryChangeResult> changeResults = restockRequest.getGoodsAmountRequests().stream().map(goodsAmountRequest -> {
            Long goodsId = goodsAmountRequest.getGoodsId();
            BigDecimal amount = goodsAmountRequest.getAmount();

            InventoryChangeResult changeResult = warehouse.increaseGoods(goodsId, amount);
            insertRecord(orderId, merchantId, goodsId, amount, GoodsTransferEnum.RESTOCK, changeResult);
            return changeResult;
        }).toList();
        return RestockResult.builder()
                .changeResults(changeResults)
                .build();
    }

    @Override
    public UpdatePriceResult updateGoodsPrice(Long goodsId, Integer assetId, BigDecimal price) {
        GoodsPriceEntity goodsPrice = goodsPriceRepository.findByGoodsIdAndAssetId(goodsId, assetId)
                .orElse(GoodsPriceEntity.builder()
                        .goods(GoodsEntity.builder().id(goodsId).build())
                        .assetId(assetId)
                        .build());
        goodsPrice.setPrice(price);
        goodsPriceRepository.save(goodsPrice);

        return UpdatePriceResult.builder()
                .goodsId(goodsId)
                .assetId(assetId)
                .price(goodsPrice.getPrice())
                .build();
    }

    @Override
    public void sendGoods(PayResult payResult, BuyRequest buyRequest) {
        String orderId = buyRequest.getOrderId();
        Integer merchantId = buyRequest.getMerchantId();

        Warehouse warehouse = warehouseFactory.getByEnum(WarehouseEnum.DEFAULT);
        for (GoodsAmountRequest goodsAmountRequest : buyRequest.getGoodsAmountRequests()) {
            Long goodsId = goodsAmountRequest.getGoodsId();
            BigDecimal amount = goodsAmountRequest.getAmount();

            InventoryChangeResult changeResult = warehouse.decreaseGoods(goodsId, amount);
            insertRecord(orderId, merchantId, goodsId, amount, GoodsTransferEnum.SALE, changeResult);
        }
    }

    private void insertRecord(String orderId, Integer merchantId, Long goodsId, BigDecimal amount, GoodsTransferEnum transferType, InventoryChangeResult inventoryChangeResult) {
        GoodsRecordEntity record = GoodsRecordEntity.builder()
                .orderId(orderId)
                .merchantId(merchantId)
                .goodsId(goodsId)
                .transferType(transferType.getType())
                .changeAmount(amount)
                .remainAmount(inventoryChangeResult.getRemainAmount())
                .build();
        goodsRecordRepository.save(record);
    }

}
