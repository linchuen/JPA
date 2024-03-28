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
            BigDecimal remainAmount = goodsInventoryEntity.remainAmount().add(amount);
            goodsInventoryEntity.remainAmount(remainAmount);
            goodsInventoryRepository.save(goodsInventoryEntity);
            return new InventoryChangeResult()
                    .goodsId(goodsId)
                    .remainAmount(remainAmount);
        });
    }

    @Override
    public InventoryChangeResult decreaseGoods(long goodsId, BigDecimal amount) {
        String key = this.getEnum().name() + goodsId;

        return customLock.tryLock(key, 1, TimeUnit.SECONDS, () -> {
            GoodsInventoryEntity goodsInventoryEntity = goodsInventoryRepository.findByGoodsId(goodsId)
                    .orElseGet(() -> createNewInventory(goodsId));
            BigDecimal remainAmount = goodsInventoryEntity.remainAmount().subtract(amount);

            if (remainAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException();
            }

            goodsInventoryEntity.remainAmount(remainAmount);
            goodsInventoryRepository.save(goodsInventoryEntity);
            return new InventoryChangeResult()
                    .goodsId(goodsId)
                    .remainAmount(remainAmount);
        });
    }

    public GoodsInventoryEntity createNewInventory(long goodsId) {
        GoodsInventoryEntity goodsInventory = new GoodsInventoryEntity()
                .remainAmount(BigDecimal.ZERO);
        return goodsInventoryRepository.save(goodsInventory);
    }

    @Override
    public WarehouseEnum getEnum() {
        return WarehouseEnum.DEFAULT;
    }
}
