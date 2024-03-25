package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;
import com.cooba.interfaces.Component;
import com.cooba.result.WalletChangeResult;

import java.math.BigDecimal;

public interface Wallet extends Component<WalletEnum> {

    WalletChangeResult increaseAsset(long userId, int assetId, BigDecimal amount);

    WalletChangeResult decreaseAsset(long userId, int assetId, BigDecimal amount);

    WalletEnum getEnum();
}
