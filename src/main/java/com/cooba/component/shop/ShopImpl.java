package com.cooba.component.shop;


import com.cooba.component.warehouse.Warehouse;
import com.cooba.component.warehouse.WarehouseFactory;
import com.cooba.entity.GoodsEntity;
import com.cooba.entity.MerchantEntity;
import com.cooba.enums.WarehouseEnum;
import com.cooba.repository.GoodsRecordRepository;
import com.cooba.repository.GoodsRepository;
import com.cooba.repository.MerchantRepository;
import com.cooba.request.CreateGoodsRequest;
import com.cooba.request.CreateMerchantRequest;
import com.cooba.request.RestockRequest;
import com.cooba.result.CreateMerchantResult;
import com.cooba.result.PayResult;
import com.cooba.result.SendGoodsResult;
import com.cooba.util.order.OrderNumGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ShopImpl implements Shop {
    private final MerchantRepository merchantRepository;
    private final GoodsRepository goodsRepository;
    private final GoodsRecordRepository goodsRecordRepository;
    private final OrderNumGenerator orderNumGenerator;
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


        Warehouse warehouse = warehouseFactory.getByEnum(WarehouseEnum.DEFAULT);

    }

    @Override
    public SendGoodsResult sendGoods(PayResult payResult) {
        Warehouse warehouse = warehouseFactory.getByEnum(WarehouseEnum.DEFAULT);
        return null;
    }


}
