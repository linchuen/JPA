package com.cooba.component.warehouse;

import com.cooba.component.FactoryTemplate;
import com.cooba.component.wallet.Wallet;
import com.cooba.enums.WalletEnum;
import com.cooba.enums.WarehouseEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WarehouseFactory extends FactoryTemplate<WarehouseEnum, Warehouse> {
    public WarehouseFactory(List<Warehouse> warehouses) {
        super(warehouses);
    }

    public Warehouse getByType(int type) {
        WarehouseEnum warehouseEnum = WarehouseEnum.getEnum(type).orElseThrow();
        return getByEnum(warehouseEnum);
    }
}
