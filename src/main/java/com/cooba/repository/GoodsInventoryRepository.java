package com.cooba.repository;

import com.cooba.entity.GoodsInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface GoodsInventoryRepository extends JpaRepository<GoodsInventoryEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<GoodsInventoryEntity> findByGoodsId(Long goodsId);
}
