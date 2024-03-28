package com.cooba.component.warehouse;

import com.cooba.entity.GoodsInventoryEntity;
import com.cooba.enums.WarehouseEnum;
import com.cooba.interfaces.Component;
import com.cooba.result.InventoryChangeResult;

import java.math.BigDecimal;

public interface Warehouse extends Component<WarehouseEnum> {

    InventoryChangeResult increaseGoods(long goodsId, BigDecimal amount);

    InventoryChangeResult decreaseGoods(long goodsId, BigDecimal amount);

    GoodsInventoryEntity createNewInventory(long goodsId);

    WarehouseEnum getEnum();
}
