package com.cooba.component.warehouse;

import com.cooba.entity.GoodsInventoryEntity;
import com.cooba.enums.WarehouseEnum;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.repository.GoodsInventoryRepository;
import com.cooba.result.InventoryChangeResult;
import com.cooba.util.lock.CustomLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultWarehouse implements Warehouse {
    private final GoodsInventoryRepository goodsInventoryRepository;
    private final CustomLock customLock;

    @Override
    public InventoryChangeResult increaseGoods(long goodsId, BigDecimal amount) {
        String key = this.getEnum().name() + goodsId;

        return customLock.tryLock(key, 1, TimeUnit.SECONDS, () -> {
            GoodsInventoryEntity goodsInventoryEntity = goodsInventoryRepository.findByGoodsId(goodsId)
                    .orElseGet(() -> createNewInventory(goodsId));
            BigDecimal remainAmount = goodsInventoryEntity.getRemainAmount().add(amount);
            goodsInventoryEntity.setRemainAmount(remainAmount);
            goodsInventoryRepository.save(goodsInventoryEntity);
            return InventoryChangeResult.builder()
                    .goodsId(goodsId)
                    .remainAmount(remainAmount)
                    .build();
        });
    }

    @Override
    public InventoryChangeResult decreaseGoods(long goodsId, BigDecimal amount) {
        String key = this.getEnum().name() + goodsId;

        return customLock.tryLock(key, 1, TimeUnit.SECONDS, () -> {
            GoodsInventoryEntity goodsInventoryEntity = goodsInventoryRepository.findByGoodsId(goodsId)
                    .orElseGet(() -> createNewInventory(goodsId));
            BigDecimal remainAmount = goodsInventoryEntity.getRemainAmount().subtract(amount);

            if (remainAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException();
            }

            goodsInventoryEntity.setRemainAmount(remainAmount);
            goodsInventoryRepository.save(goodsInventoryEntity);
            return InventoryChangeResult.builder()
                    .goodsId(goodsId)
                    .remainAmount(remainAmount)
                    .build();
        });
    }

    public GoodsInventoryEntity createNewInventory(long goodsId) {
        GoodsInventoryEntity goodsInventory = GoodsInventoryEntity.builder()
                .remainAmount(BigDecimal.ZERO)
                .build();
        return goodsInventoryRepository.save(goodsInventory);
    }

    @Override
    public WarehouseEnum getEnum() {
        return WarehouseEnum.DEFAULT;
    }
}
