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
import com.cooba.request.*;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.InventoryChangeResult;
import com.cooba.result.PayResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    public void createGoods(CreateGoodsRequest createGoodsRequest) {
        LocalDateTime now = LocalDateTime.now();
        GoodsEntity goodsEntity = GoodsEntity.builder()
                .merchantId(createGoodsRequest.getMerchantId())
                .name(createGoodsRequest.getName())
                .createdTime(now)
                .updateTime(now)
                .build();
        goodsRepository.save(goodsEntity);
    }

    @Override
    public void restockGoods(RestockRequest restockRequest) {
        String orderId = restockRequest.getOrderId();
        Integer merchantId = restockRequest.getMerchantId();

        Warehouse warehouse = warehouseFactory.getByEnum(WarehouseEnum.DEFAULT);
        for (GoodsAmountRequest goodsAmountRequest : restockRequest.getGoodsAmountRequests()) {
            Long goodsId = goodsAmountRequest.getGoodsId();
            BigDecimal amount = goodsAmountRequest.getAmount();

            InventoryChangeResult changeResult = warehouse.decreaseGoods(merchantId, goodsId, amount);
            insertRecord(orderId, merchantId, goodsId, amount, GoodsTransferEnum.SALE, changeResult);
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
