package com.cooba.repository;

import com.cooba.entity.GoodsPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsPriceRepository extends JpaRepository<GoodsPriceEntity, Long> {

    Optional<GoodsPriceEntity> findByGoodsIdAndAssetId(Long goodsId, Integer assetId);
}
