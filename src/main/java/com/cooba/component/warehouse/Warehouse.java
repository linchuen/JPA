package com.cooba.component.warehouse;

import com.cooba.enums.WarehouseEnum;
import com.cooba.interfaces.Component;
import com.cooba.result.InventoryChangeResult;

import java.math.BigDecimal;

public interface Warehouse extends Component<WarehouseEnum> {

    InventoryChangeResult increaseGoods(int merchantId, long goodsId, BigDecimal amount);

    InventoryChangeResult decreaseGoods(int merchantId, long goodsId, BigDecimal amount);

    WarehouseEnum getEnum();
}
