package com.cooba.component.wallet;

import com.cooba.component.FactoryTemplate;
import com.cooba.enums.WalletEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WalletFactory extends FactoryTemplate<WalletEnum, Wallet> {
    public WalletFactory(List<Wallet> wallets) {
        super(wallets);
    }

    public Wallet getByType(int type) {
        WalletEnum walletEnum = WalletEnum.getEnum(type).orElseThrow();
        return getByEnum(walletEnum);
    }
}
