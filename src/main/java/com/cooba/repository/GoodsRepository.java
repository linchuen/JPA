package com.cooba.repository;

import com.cooba.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {

    List<GoodsEntity> findByGoodsIdIn(Collection<Long> goodsIds);
}
