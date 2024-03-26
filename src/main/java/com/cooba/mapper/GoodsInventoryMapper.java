package com.cooba.mapper;

import com.cooba.dto.InventoryPriceDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface GoodsInventoryMapper {
    List<InventoryPriceDto> findWithPriceByIds(Collection<Long> goodsIdList);
}
