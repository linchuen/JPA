package com.cooba.repository;

import com.cooba.entity.GoodsInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsInventoryRepository extends JpaRepository<GoodsInventoryEntity, Long> {

    Optional<GoodsInventoryEntity> findByMerchantIdAndGoodsId(Integer merchantId, Long goodsId);
}
