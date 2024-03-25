package com.cooba.repository;

import com.cooba.entity.GoodsOrderEntity;
import com.cooba.entity.GoodsPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsOrderRepository extends JpaRepository<GoodsOrderEntity, Long> {
}
