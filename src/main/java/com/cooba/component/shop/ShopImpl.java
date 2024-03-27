package com.cooba.component.shop;


import com.cooba.component.warehouse.Warehouse;
import com.cooba.component.warehouse.WarehouseFactory;
import com.cooba.entity.GoodsEntity;
import com.cooba.entity.GoodsRecordEntity;
import com.cooba.entity.MerchantEntity;
import com.cooba.enums.GoodsTransferEnum;
import com.cooba.enums.WarehouseEnum;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ShopImpl implements Shop {
    private final MerchantRepository merchantRepository;
    private final GoodsRepository goodsRepository;
    private final GoodsRecordRepository goodsRecordRepository;
    private final WarehouseFactory warehouseFactory;

    @Override
    public CreateMerchantResult createMerchant(CreateMerchantRequest createMerchantRequest) {
        LocalDateTime now = LocalDateTime.now();
        MerchantEntity merchantEntity = MerchantEntity.builder()
                .name(createMerchantRequest.getName())
                .createdTime(now)
                .updateTime(now)
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
        LocalDateTime now = LocalDateTime.now();
        Integer merchantId = createGoodsRequest.getMerchantId();
        String name = createGoodsRequest.getName();

        GoodsEntity goodsEntity = GoodsEntity.builder()
                .merchantId(merchantId)
                .name(name)
                .createdTime(now)
                .updateTime(now)
                .build();
        goodsRepository.save(goodsEntity);

        Long goodsId = goodsEntity.getId();

        Warehouse warehouse = warehouseFactory.getByEnum(WarehouseEnum.DEFAULT);
        warehouse.createNewInventory(merchantId, goodsId);

        return CreateGoodsResult.builder()
                .goodsId(goodsId)
                .name(name)
                .build();
    }

    @Override
    public void restockGoods(RestockRequest restockRequest) {
        String orderId = restockRequest.getOrderId();
        Integer merchantId = restockRequest.getMerchantId();

        Warehouse warehouse = warehouseFactory.getByEnum(WarehouseEnum.DEFAULT);
        for (GoodsAmountRequest goodsAmountRequest : restockRequest.getGoodsAmountRequests()) {
            Long goodsId = goodsAmountRequest.getGoodsId();
            BigDecimal amount = goodsAmountRequest.getAmount();

            InventoryChangeResult changeResult = warehouse.increaseGoods(merchantId, goodsId, amount);
            insertRecord(orderId, merchantId, goodsId, amount, GoodsTransferEnum.RESTOCK, changeResult);
        }
    }

    @Override
    public void sendGoods(PayResult payResult, BuyRequest buyRequest) {
        String orderId = buyRequest.getOrderId();
        Integer merchantId = buyRequest.getMerchantId();

        Warehouse warehouse = warehouseFactory.getByEnum(WarehouseEnum.DEFAULT);
        for (GoodsAmountRequest goodsAmountRequest : buyRequest.getGoodsAmountRequests()) {
            Long goodsId = goodsAmountRequest.getGoodsId();
            BigDecimal amount = goodsAmountRequest.getAmount();

            InventoryChangeResult changeResult = warehouse.decreaseGoods(merchantId, goodsId, amount);
            insertRecord(orderId, merchantId, goodsId, amount, GoodsTransferEnum.SALE, changeResult);
        }
    }

    private void insertRecord(String orderId, Integer merchantId, Long goodsId, BigDecimal amount, GoodsTransferEnum transferType, InventoryChangeResult inventoryChangeResult) {
        LocalDateTime now = LocalDateTime.now();
        GoodsRecordEntity record = GoodsRecordEntity.builder()
                .orderId(orderId)
                .merchantId(merchantId)
                .goodsId(goodsId)
                .transferType(transferType.getType())
                .changeAmount(amount)
                .remainAmount(inventoryChangeResult.getRemainAmount())
                .createdTime(now)
                .updatedTime(now)
                .build();
        goodsRecordRepository.save(record);
    }

}
