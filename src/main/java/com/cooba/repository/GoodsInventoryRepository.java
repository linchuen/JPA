package com.cooba.repository;

import com.cooba.entity.GoodsInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsInventoryRepository extends JpaRepository<GoodsInventoryEntity, Long> {

    Optional<GoodsInventoryEntity> findByGoodsId(Long goodsId);
}
