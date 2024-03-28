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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultWarehouse implements Warehouse {
    private final CustomLock customLock;
    //Repository
    private final GoodsInventoryRepository goodsInventoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InventoryChangeResult increaseGoods(long goodsId, BigDecimal amount) {
        String key = this.getEnum().name() + goodsId;

        return customLock.tryLock(key, 3, TimeUnit.SECONDS, () -> {
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
    @Transactional(rollbackFor = Exception.class)
    public InventoryChangeResult decreaseGoods(long goodsId, BigDecimal amount) {
        String key = this.getEnum().name() + goodsId;

        return customLock.tryLock(key, 3, TimeUnit.SECONDS, () -> {
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
